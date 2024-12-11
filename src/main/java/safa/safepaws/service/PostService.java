package safa.safepaws.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import safa.safepaws.dto.post.*;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.PostStatus;
import safa.safepaws.mapper.PostMapper;
import safa.safepaws.model.AdoptionContract;
import safa.safepaws.model.Post;
import safa.safepaws.model.Request;
import safa.safepaws.model.User;
import safa.safepaws.repository.AdoptionContractRepository;
import safa.safepaws.repository.PostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private PostMapper postMapper;
    private final User authenticatedUser;
    private final CloudinaryService cloudinaryService;
    private final AddressService addressService;
    private final PdfService pdfService;
    private final AdoptionContractRepository adoptionContractRepository;

    /**
     * Find a post by id
     *
     * @param postId Integer
     * @return Post
     */
    public Post findPost(Integer postId){
        Post post = postRepository.findTopById(postId).orElse(null);

        if (post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return post;
    }

    /**
     * Check if the authenticated user is the owner of the post
     *
     * @param post Post
     * @return Boolean
     */
    private Boolean checkOwner(Post post){
        return Objects.equals(post.getClient().getId(), authenticatedUser.getClient().getId());
    }

    /**
     * Get a post
     *
     * @param postId Integer
     * @return GetPostResponse
     */
    public GetPostResponse getPost(Integer postId) {
        return postMapper.toDTO(findPost(postId));
    }

    /**
     * Create a new post
     *
     * @param createPostRequest PostCreateDTO
     * @return Integer Post id
     */
    public Integer createPost(CreatePostRequest createPostRequest, MultipartFile file){
        if (file == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is required");
        }

        createPostRequest.setPhoto(cloudinaryService.uploadImage(file));

        Post post = postMapper.toEntity(createPostRequest);

        post.setUrgent(false);
        post.setDeleted(false);
        post.setStatus(PostStatus.PENDING);
        post.setCreationDate(LocalDate.now());
        post.setCode(NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 10).toUpperCase());

        post.setClient(authenticatedUser.getClient());

        post.setAddress(addressService.createAddress(createPostRequest.getAddress()));

        return postRepository.save(post).getId();
    }

    /**
     * Edit a post
     *
     * @param editPostRequest EditPostRequest
     * @param postId Integer
     * @return Post
     */
    public Post editPost(EditPostRequest editPostRequest, Integer postId){
        Post post = postMapper.toEntity(editPostRequest);
        Post postEdited = findPost(postId);

        if (checkOwner(postEdited)){
            if (!post.getPhoto().isBlank()){
                postEdited.setPhoto(post.getPhoto());
            }

            postEdited.setName(post.getName());
            postEdited.setDescription(post.getDescription());
            postEdited.setType(post.getType());
            postEdited.setAddress(post.getAddress());
        }

        return postEdited;
    }

    /**
     * Delete a post
     *
     * @param postId Integer
     * @return Post
     */
    public Post deletePost(Integer postId){
        Post post = findPost(postId);

        if (checkOwner(post)){
            post.setDeleted(true);
        }

        return post;
    }

    /**
     * Update the status of a post
     *
     * @param postId Integer
     * @return Post
     */
    public Post updateStatus(Integer postId){
        Post post = findPost(postId);

        if (checkOwner(post)){
            post.setStatus(PostStatus.ADOPTED);
        }

        return post;
    }

    /**
     * Get all posts
     *
     * @return List<Post>
     */
    public List<GetPostResponse> getPosts(String filter) {
        if (Objects.equals(filter, "") || filter == null){
            return postMapper.toDTO(postRepository.findAvailableAdoptions(authenticatedUser.getClient().getId()));
        }

        List<Integer> filters = Arrays.stream(filter.split(","))
                .map(Integer::parseInt)
                .toList();

        return postMapper.toDTO(postRepository.findPendingPostsByTypes(filters));
    }

    public Map<String, Integer> getAnimalTypes() {
        Map<String, Integer> response = new HashMap<>();

        for(AnimalType animalType : AnimalType.values()){
            String key = animalType.name().toLowerCase().substring(0, 1).toUpperCase() + animalType.name().substring(1).toLowerCase();
            response.put(key, animalType.getId());
        }

        return response;
    }

    public List<GetPostResponse> getUserPost(){
        return postMapper.toDTO(postRepository.findAllByClientIdAndDeletedFalseOrderByStatus(authenticatedUser.getClient().getId()));
    }

    public List<MapPostResponse> getMapPosts(MapPostRequest mapPostRequest) {
        return postMapper.toMapDTO(postRepository.findWithinBounds(
            mapPostRequest.getSouthWest().getLatitude(),
            mapPostRequest.getSouthWest().getLongitude(),
            mapPostRequest.getNorthEast().getLatitude(),
            mapPostRequest.getNorthEast().getLongitude()
        ));
    }

    public Context getContext(Post post, Request request, AdoptionContract adoptionContract) {
        if (adoptionContract == null) {
            adoptionContract = adoptionContractRepository.findByPostId(post.getId()).orElse(null);

            if (adoptionContract == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption contract not found");
            }
        }

        Context context = new Context();
        context.setVariable("post", post);
        context.setVariable("request", request);
        context.setVariable("owner", authenticatedUser.getClient());
        context.setVariable("adopter", request.getClient());

        if (adoptionContract.getOwnerSignature() != null) {
            context.setVariable("owner_signed", true);
        }

        if (adoptionContract.getAdopterSignature() != null) {
            context.setVariable("adopter_signed", true);
        }

        context.setVariable("owner_signature", adoptionContract.getOwnerSignature() == null ? "Pendiente de firma" : adoptionContract.getOwnerSignature());
        context.setVariable("adopter_signature", adoptionContract.getAdopterSignature() == null ? "Pendiente de firma" : adoptionContract.getAdopterSignature());

        return context;
    }

    public Context getContext(Post post, Request request) {
        return getContext(post, request, null);
    }

    @Transactional
    public void generateAdoptionContract(Post post, Request request) throws Exception {
        post.setStatus(PostStatus.ADOPTED);
        postRepository.save(post);

        AdoptionContract adoptionContract = new AdoptionContract();
        adoptionContract.setPost(post);
        adoptionContract.setDocument(pdfService.generatePdfAsBase64("adoption-contract", getContext(post, request, adoptionContract)));
        adoptionContractRepository.save(adoptionContract);
    }

    public void saveSignature(Post post, Request request, String signature, Boolean isOwner) {
        AdoptionContract adoptionContract = adoptionContractRepository.findByPostId(post.getId()).orElse(null);

        if (adoptionContract == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption contract not found");
        }

        if (isOwner) {
            adoptionContract.setOwnerSignature(signature);
            adoptionContract.setOwnerSignatureDate(LocalDateTime.now());
        } else {
            adoptionContract.setAdopterSignature(signature);
            adoptionContract.setAdopterSignatureDate(LocalDateTime.now());
        }

        if (adoptionContract.getOwnerSignature() != null && adoptionContract.getAdopterSignature() != null) {
            post.setStatus(PostStatus.ADOPTED);
            postRepository.save(post);

            try {
                String document = pdfService.generatePdfAsBase64("adoption-contract", getContext(post, request, adoptionContract));
                adoptionContract.setDocument(document);

                String sha256 = pdfService.generateSha256FromBase64(document);
                adoptionContract.setDocumentSha256(sha256);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating hash");
            }
        }

        adoptionContractRepository.save(adoptionContract);
    }

}

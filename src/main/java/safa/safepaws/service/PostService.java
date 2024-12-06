package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.post.*;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.PostStatus;
import safa.safepaws.mapper.PostMapper;
import safa.safepaws.model.Post;
import safa.safepaws.model.User;
import safa.safepaws.repository.PostRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private PostMapper postMapper;
    private final User authenticatedUser;
    private final CloudinaryService cloudinaryService;
    private final AddressService addressService;

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
            return postMapper.toDTO(postRepository.findAvailableAdoptions());
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

}

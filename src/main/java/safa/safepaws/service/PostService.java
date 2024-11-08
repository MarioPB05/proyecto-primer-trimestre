package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.post.CreatePostRequest;
import safa.safepaws.dto.post.EditPostRequest;
import safa.safepaws.enums.PostStatus;
import safa.safepaws.mapper.PostMapper;
import safa.safepaws.model.Post;
import safa.safepaws.model.User;
import safa.safepaws.repository.PostRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private PostMapper postMapper;
    private final User authenticatedUser;

    /**
     * Find a post by id
     *
     * @param postId Integer
     * @return Post
     */
    private Post findPost(Integer postId){
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
     * Create a new post
     *
     * @param createPostRequest PostCreateDTO
     * @return Post
     */
    public Post createPost(CreatePostRequest createPostRequest){
        Post post = postMapper.toEntity(createPostRequest);
        return postRepository.save(post);
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

}

package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import safa.safepaws.dto.post.GetPostResponse;
import safa.safepaws.model.Post;
import safa.safepaws.model.User;
import safa.safepaws.repository.PostRepository;
import safa.safepaws.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final User authenticatedUser;


    @GetMapping("/")
    public ResponseEntity<List<GetPostResponse>> getPosts(@RequestParam(required = false) String filter){
        return ResponseEntity.ok(postService.getPosts(filter));
    }

    @GetMapping("/user")
    public ResponseEntity<List<GetPostResponse>> getClientPosts() {
        try {
            List<GetPostResponse> posts = postService.getUserPost();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/animalTypes")
    public ResponseEntity<Map<String, Integer>> getAnimalTypes(){
        return ResponseEntity.ok(postService.getAnimalTypes());
    }
}

package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safa.safepaws.dto.post.CreatePostRequest;
import safa.safepaws.dto.post.GetPostResponse;
import safa.safepaws.dto.post.MapPostRequest;
import safa.safepaws.dto.post.MapPostResponse;
import safa.safepaws.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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

    @PostMapping("/new")
    public ResponseEntity<Integer> createPost(@RequestPart("dto")CreatePostRequest createPostRequest,
                                              @RequestPart(value="file", required = false) MultipartFile file){
        return ResponseEntity.ok(postService.createPost(createPostRequest, file));
    }

    @PostMapping("/map")
    public ResponseEntity<List<MapPostResponse>> getMapPosts(@RequestBody MapPostRequest mapPostRequest){
        return ResponseEntity.ok(postService.getMapPosts(mapPostRequest));
    }
}

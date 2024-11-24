package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import safa.safepaws.dto.post.GetPostResponse;
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

    @GetMapping("/animalTypes")
    public ResponseEntity<Map<String, Integer>> getAnimalTypes(){
        return ResponseEntity.ok(postService.getAnimalTypes());
    }
}

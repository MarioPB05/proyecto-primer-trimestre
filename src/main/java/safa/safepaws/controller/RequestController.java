package safa.safepaws.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/received")
    public ResponseEntity<List<GetAdoptionsResponse>> getAdoptionResponseById() {
        return ResponseEntity.ok(requestService.getReceivedAdoptionsResponses());
    }

    @GetMapping("/sent")
    public ResponseEntity<List<GetAdoptionsResponse>>getAdoptionsResponse(){
        return ResponseEntity.ok(requestService.getSentAdoptionsResponses());
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Integer> createRequest(@RequestBody RequestCreateDTO requestCreateDTO){
        return ResponseEntity.ok(requestService.createRequest(requestCreateDTO));
    }
}
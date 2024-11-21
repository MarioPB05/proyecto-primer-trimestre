package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.service.RequestService;

import java.util.List;
import java.util.Optional;

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
}
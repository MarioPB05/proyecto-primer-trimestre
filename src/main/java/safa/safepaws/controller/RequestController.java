package safa.safepaws.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.request.RequestStatusResponse;
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
    public ResponseEntity<String> createRequest(@RequestBody RequestCreateDTO requestCreateDTO){
        return ResponseEntity.ok(requestService.createRequest(requestCreateDTO));
    }

    @GetMapping("/{requestCode}/pdf")
    @ResponseBody
    public void generateRequestPdf(@PathVariable String requestCode, HttpServletResponse response) throws Exception {
        requestService.generateRequestPdf(requestCode, response);
    }

    @GetMapping("/{requestCode}/status")
    public ResponseEntity<RequestStatusResponse> getRequestStatus(@PathVariable String requestCode){
        return ResponseEntity.ok(requestService.getRequestStatus(requestCode));
    }

    @GetMapping("/{requestCode}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable String requestCode){
        return ResponseEntity.ok(requestService.acceptRequest(requestCode));
    }
}
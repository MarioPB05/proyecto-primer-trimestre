package safa.safepaws.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safa.safepaws.dto.authentication.AuthenticationRequest;
import safa.safepaws.dto.authentication.AuthenticationResponse;
import safa.safepaws.dto.authentication.RegisterRequest;
import safa.safepaws.dto.authentication.VerifyAuthResponse;
import safa.safepaws.service.AuthenticationService;
import safa.safepaws.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/verify-token")
    public ResponseEntity<VerifyAuthResponse> verifyToken(@RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(userService.verifyToken(token));
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestPart("dto") RegisterRequest registerRequest,
                                                           @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(authenticationService.register(registerRequest, file));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }

}

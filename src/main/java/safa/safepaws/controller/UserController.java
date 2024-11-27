package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import safa.safepaws.dto.user.EditUserRequest;
import safa.safepaws.dto.user.GetUserResponse;
import safa.safepaws.service.CloudinaryService;
import safa.safepaws.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @PutMapping("/edit")
    public ResponseEntity<GetUserResponse> editUser(@RequestPart("dto") EditUserRequest editUserRequest,
                                                    @RequestPart(value="file", required = false) MultipartFile file ) {
        try {
            if (file != null) {
                editUserRequest.getClient().setPhoto(cloudinaryService.uploadImage(file));
            }

            return ResponseEntity.ok(userService.editUser(editUserRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserResponse> getAuthenticatedUser() {
        return ResponseEntity.ok(userService.getUserData());
    }

}

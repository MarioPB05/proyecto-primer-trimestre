package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.authentication.AuthenticationRequest;
import safa.safepaws.dto.authentication.AuthenticationResponse;
import safa.safepaws.dto.user.CreateUserRequest;
import safa.safepaws.enums.Role;
import safa.safepaws.model.User;
import safa.safepaws.repository.UserRepository;
import safa.safepaws.security.JwtService;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private boolean checkIfUserExists(String username) {
        return userRepository.findTopByUsername(username).isPresent();
    }

    public AuthenticationResponse register(CreateUserRequest createUserRequest) {
        if (createUserRequest.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }

        if (createUserRequest.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        if (createUserRequest.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        if (checkIfUserExists(createUserRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        var user = new User().builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .email(createUserRequest.getEmail())
                .client(createUserRequest.getClient())
                .rol(Role.USER)
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        var user = userRepository.findTopByUsername(registerRequest.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

}

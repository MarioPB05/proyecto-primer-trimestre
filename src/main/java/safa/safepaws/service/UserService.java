package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public AuthenticationResponse register(CreateUserRequest createUserRequest) {
        var user = new User().builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .email(createUserRequest.getEmail())
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

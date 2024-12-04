package safa.safepaws.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.authentication.AuthenticationRequest;
import safa.safepaws.dto.authentication.AuthenticationResponse;
import safa.safepaws.dto.authentication.VerifyAuthResponse;
import safa.safepaws.dto.user.CreateUserRequest;
import safa.safepaws.dto.user.EditUserRequest;
import safa.safepaws.dto.user.GetUserResponse;
import safa.safepaws.enums.Role;
import safa.safepaws.mapper.UserMapper;
import safa.safepaws.model.User;
import safa.safepaws.repository.UserRepository;
import safa.safepaws.security.JwtService;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final User authenticatedUser;
    private final UserMapper userMapper;
    private final ClientService clientService;


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

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findTopByUsername(authenticationRequest.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    private VerifyAuthResponse notValidToken() {
        return VerifyAuthResponse.builder().valid(false).build();
    }

    private VerifyAuthResponse validToken(Date expiresAt) {
        return VerifyAuthResponse.builder().valid(true).expiresAt(expiresAt.toString()).build();
    }

    public VerifyAuthResponse verifyToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return notValidToken();
        }

        try {
            token = token.substring(7);
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails == null || !jwtService.isTokenValid(token, userDetails)) {
                return notValidToken();
            }

            return validToken(jwtService.extractClaim(token, Claims::getExpiration));
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return notValidToken();
        }
    }

    private boolean isNotEmptyField(String field) {
        return field != null && !field.isEmpty();
    }

    public GetUserResponse editUser(EditUserRequest dto) {
        Integer userid = authenticatedUser.getId();
        User user = userRepository.findById(userid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (isNotEmptyField(dto.getUsername())) user.setUsername(dto.getUsername());
        if (isNotEmptyField(dto.getEmail())) user.setEmail(dto.getEmail());

        userRepository.save(user);

        clientService.modifyClient(dto.getClient());

        return userMapper.toDTO(user);
    }

    public GetUserResponse getUserData() {
        try {
            Integer userId = authenticatedUser.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            return userMapper.toDTO(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting user data");
        }
    }
}

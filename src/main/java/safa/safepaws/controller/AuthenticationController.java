package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import safa.safepaws.dto.address.CreateAddressRequest;
import safa.safepaws.dto.authentication.AuthenticationRequest;
import safa.safepaws.dto.authentication.AuthenticationResponse;
import safa.safepaws.dto.authentication.RegisterRequest;
import safa.safepaws.dto.user.CreateUserRequest;
import safa.safepaws.model.Address;
import safa.safepaws.model.Client;
import safa.safepaws.service.AddressService;
import safa.safepaws.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AddressService addressService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        CreateAddressRequest createAddressRequest = new CreateAddressRequest(
            registerRequest.getCoordinateX(),
            registerRequest.getCoordinateY(),
            registerRequest.getRoad(),
            registerRequest.getNeighborhood(),
            registerRequest.getVillage(),
            registerRequest.getProvince(),
            registerRequest.getState(),
            registerRequest.getPostcode(),
            registerRequest.getCountry(),
            registerRequest.getCountryCode()
        );

        Address address = addressService.createAddress(createAddressRequest);

        Client client = new Client(); // TODO: Implement client creation

        CreateUserRequest createUserRequest = new CreateUserRequest(
            registerRequest.getUsername(),
            registerRequest.getPassword(),
            registerRequest.getEmail(),
            client
        );

        return ResponseEntity.ok(userService.register(createUserRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest registerRequest) {
        return ResponseEntity.ok(userService.authenticate(registerRequest));
    }

}

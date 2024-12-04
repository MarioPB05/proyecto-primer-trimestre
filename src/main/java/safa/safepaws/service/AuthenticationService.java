package safa.safepaws.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.address.CreateAddressRequest;
import safa.safepaws.dto.authentication.AuthenticationResponse;
import safa.safepaws.dto.authentication.RegisterRequest;
import safa.safepaws.dto.client.CreateClientRequest;
import safa.safepaws.dto.user.CreateUserRequest;
import safa.safepaws.model.Address;
import safa.safepaws.model.Client;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AddressService addressService;
    private final ClientService clientService;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest, MultipartFile file) {
        if (file == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is required");
        }

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

        CreateClientRequest createClientRequest = new CreateClientRequest(
                registerRequest.getName(),
                registerRequest.getSurname(),
                registerRequest.getDni(),
                registerRequest.getBirthdate(),
                null,
                address,
                cloudinaryService.uploadImage(file)
        );

        Client client = clientService.createClient(createClientRequest);

        CreateUserRequest createUserRequest = new CreateUserRequest(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getEmail(),
                client
        );

        return userService.register(createUserRequest);
    }

}

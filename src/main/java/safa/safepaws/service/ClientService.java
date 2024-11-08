package safa.safepaws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.client.CreateClientRequest;
import safa.safepaws.dto.client.EditClientRequest;
import safa.safepaws.mapper.ClientMapper;
import safa.safepaws.model.Client;
import safa.safepaws.model.User;
import safa.safepaws.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final User authenticatedUser;

    public Client createClient(CreateClientRequest createClientRequest) {
        Client client = clientMapper.toEntity(createClientRequest);
        return clientRepository.save(client);
    }


    public Client modifyClient(EditClientRequest editClientRequest) {
        if (authenticatedUser.getClient().getId().equals(authenticatedUser.getClient().getId())) {
            Client client = clientRepository.findById(editClientRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setName(editClientRequest.getName());
            client.setSurname(editClientRequest.getSurname());
            client.setBirthdate(editClientRequest.getBirthdate());
            client.setDni(editClientRequest.getDni());
            client.setAddress(editClientRequest.getAddress());
            return clientRepository.save(client);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not authorized to modify this client");
        }
    }


}
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

    private boolean isNotEmptyField(String field) {
        return field != null && !field.isEmpty();
    }

    public void modifyClient(EditClientRequest editClientRequest) {
        Client client = clientRepository.findById(authenticatedUser.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not found"));

        if (isNotEmptyField(editClientRequest.getName())) {
            client.setName(editClientRequest.getName());
        }

        if (isNotEmptyField(editClientRequest.getSurname())) {
            client.setSurname(editClientRequest.getSurname());
        }

        if (isNotEmptyField(editClientRequest.getDni())) {
            client.setDni(editClientRequest.getDni());
        }

        if (isNotEmptyField(editClientRequest.getPhoto())) {
            client.setPhoto(editClientRequest.getPhoto());
        }

        clientRepository.save(client);
    }


}
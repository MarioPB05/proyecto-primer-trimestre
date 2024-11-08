package safa.safepaws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import safa.safepaws.dto.ClientCreateDTO;
import safa.safepaws.dto.ClientEditDTO;
import safa.safepaws.mapper.ClientMapper;
import safa.safepaws.model.Client;
import safa.safepaws.model.User;
import safa.safepaws.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper = ClientMapper.INSTANCE;
    private final User AuthenticatedUser;
    private final User authenticatedUser;

    public Client createClient(ClientCreateDTO clientCreateDTO) {
        Client client = clientMapper.toEntity(clientCreateDTO);
        return clientRepository.save(client);
    }

    public Client modifyClient(ClientEditDTO clientEditDTO) {
        if (AuthenticatedUser.getClient().getId() == authenticatedUser.getClient().getId()) {
            Client client = clientRepository.findById(clientEditDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setName(clientEditDTO.getName());
            client.setSurname(clientEditDTO.getSurname());
            client.setBirthdate(clientEditDTO.getBirthdate());
            client.setDni(clientEditDTO.getDni());
            client.setAddress(clientEditDTO.getAddress());
            return clientRepository.save(client);
        } else {
            throw new RuntimeException("You are not authorized to modify this client");
        }
    }


}
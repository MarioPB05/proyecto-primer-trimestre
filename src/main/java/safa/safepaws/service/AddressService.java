package safa.safepaws.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.safepaws.dto.address.CreateAddressRequest;
import safa.safepaws.mapper.AddressMapper;
import safa.safepaws.model.Address;
import safa.safepaws.repository.AddressRepository;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Address createAddress(CreateAddressRequest createAddressRequest) {
        Address address = addressMapper.toEntity(createAddressRequest);
        return addressRepository.save(address);
    }

}

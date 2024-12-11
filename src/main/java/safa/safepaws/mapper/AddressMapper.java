package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import safa.safepaws.dto.address.CreateAddressRequest;
import safa.safepaws.model.Address;

@Mapper
public interface AddressMapper {

    Address toEntity(CreateAddressRequest createAddressRequest);

}

package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import safa.safepaws.dto.ClientCreateDTO;
import safa.safepaws.dto.ClientEditDTO;
import safa.safepaws.model.Client;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(source = "addressId", target = "address.id")
    Client toEntity(ClientCreateDTO clientCreateDTO);

    @Mapping(source = "address.id", target = "addressId")
    ClientCreateDTO toCreateDTO(Client client);

}
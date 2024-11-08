package safa.safepaws.mapper;

import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import safa.safepaws.dto.client.CreateClientRequest;
import safa.safepaws.model.Client;

import java.time.LocalDate;

@Mapper
public abstract class ClientMapper {

    @Mapping(target = "birthdate", source = "birthdate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "registrationDate", source = "registrationDate", qualifiedByName = "currentDate")
    public abstract Client toEntity(CreateClientRequest createClientRequest);

    @Named("stringToLocalDate")
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

    @Named("currentDate")
    public static LocalDate currentDate(String date) {
        return LocalDate.now();
    }
}

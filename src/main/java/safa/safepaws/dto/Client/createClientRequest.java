package safa.safepaws.dto.Client;

import lombok.Data;
import safa.safepaws.model.Address;

import java.time.LocalDate;

@Data
public class createClientRequest {
    private Integer id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private Address address;

}

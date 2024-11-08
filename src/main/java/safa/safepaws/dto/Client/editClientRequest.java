package safa.safepaws.dto.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.model.Address;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor

@Data
public class editClientRequest {

    private Integer id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private Address address;
}

package safa.safepaws.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.model.Address;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditClientRequest {

    private Integer id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private Address address;
}

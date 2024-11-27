package safa.safepaws.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.model.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientRequest {
    private String name;
    private String surname;
    private String dni;
    private String birthdate;
    private String registrationDate;
    private Address address;
    private String photo;
}

package safa.safepaws.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientCreateDTO {
    private Integer id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private String Address;

}

package safa.safepaws.dto.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {
    private Float coordinateX;
    private Float coordinateY;
    private String road;
    private String neighborhood;
    private String village;
    private String province;
    private String state;
    private String postcode;
    private String country;
    private String countryCode;
}

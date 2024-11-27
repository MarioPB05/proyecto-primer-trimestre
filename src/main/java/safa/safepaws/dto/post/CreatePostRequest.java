package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.dto.address.CreateAddressRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private String name;
    private String description;
    private String photo;
    private Integer typeId;
    private CreateAddressRequest address;
}

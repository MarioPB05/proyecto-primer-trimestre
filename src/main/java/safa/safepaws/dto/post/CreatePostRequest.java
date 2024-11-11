package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import safa.safepaws.model.Address;
import safa.safepaws.model.Client;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private Client client;
    private String name;
    private String description;
    private MultipartFile photo;
    private Integer typeId;
    private Address address;
}

package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import safa.safepaws.model.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditPostRequest {
    private String name;
    private String description;
    private String photo; // TODO implementar cloudinary (cambiar a multipartFile)
    private Integer typeId;
    private Address address;
}

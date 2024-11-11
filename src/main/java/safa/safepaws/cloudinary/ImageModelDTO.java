package safa.safepaws.cloudinary;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageModelDTO {
    private String name;
    private MultipartFile file;
}

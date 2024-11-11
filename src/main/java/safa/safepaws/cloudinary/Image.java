package safa.safepaws.cloudinary;



import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name_image")
    private String name;

    @Column(name = "url_image")
    private String url;

}

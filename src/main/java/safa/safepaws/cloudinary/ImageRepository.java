package safa.safepaws.cloudinary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository  extends JpaRepository<Image, UUID> {
}

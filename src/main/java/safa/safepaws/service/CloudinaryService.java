package safa.safepaws.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile image) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image");
        }

        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + image.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(image.getBytes());
            fos.close();

            var pic = cloudinary.uploader().upload(convFile, ObjectUtils.asMap("folder", "/safePaws/"));

            return pic.get("url").toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error uploading image");
        }
    }
}

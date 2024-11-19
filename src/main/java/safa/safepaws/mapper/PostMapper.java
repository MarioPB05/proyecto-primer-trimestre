package safa.safepaws.mapper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.post.CreatePostRequest;
import safa.safepaws.dto.post.EditPostRequest;
import safa.safepaws.dto.post.GetPostResponse;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.PostStatus;
import safa.safepaws.model.Post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Mapper
public abstract class PostMapper {

    @Autowired
    private Cloudinary cloudinary;

    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "uploadImageOnCreate")
    public abstract Post toEntity(CreatePostRequest createPostRequest);

    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    @Mapping(target = "photo", source = "photo", qualifiedByName = "uploadImageOnEdit")
    public abstract Post toEntity(EditPostRequest editPostRequest);

    @Mapping(target = "addressVillage", source = "address.village")
    @Mapping(target = "typeId", source = "type", qualifiedByName = "mapAnimalType")
    @Mapping(target = "statusId", source = "status", qualifiedByName = "mapPostStatus")
    @Mapping(target = "description", source = "description")
    public abstract GetPostResponse toDTO(Post post);
    public abstract List<GetPostResponse> toDTO(List<Post> postList);

    @Named("mapAnimalType")
    public Integer mapAnimalType(AnimalType animalType) {
        return animalType != null ? animalType.ordinal() : null;
    }

    @Named("mapPostStatus")
    public Integer mapPostStatus(PostStatus postStatus) {
        return postStatus != null ? postStatus.ordinal() : null;
    }

    @Named("toAnimalType")
    public AnimalType toAnimalType(Integer typeId){
        return AnimalType.fromId(typeId);
    }

    @Named("uploadImageOnCreate")
    public String uploadImageOnCreate(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is required");
        }

        return uploadImage(image);
    }

    @Named("uploadImageOnEdit")
    public String uploadImageOnEdit(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            // Como estamos editando, no es necesario que la imagen sea requerida
            return null;
        }

        return uploadImage(image);
    }

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

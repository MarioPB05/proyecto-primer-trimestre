package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import safa.safepaws.dto.post.CreatePostRequest;
import safa.safepaws.dto.post.EditPostRequest;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.model.Post;

@Mapper
public abstract class PostMapper {
    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    public abstract Post toEntity(CreatePostRequest createPostRequest);

    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    public abstract Post toEntity(EditPostRequest editPostRequest);

    @Named("toAnimalType")
    public AnimalType toAnimalType(Integer typeId){
        return AnimalType.fromId(typeId);
    }
}

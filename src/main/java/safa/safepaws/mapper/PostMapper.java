package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import safa.safepaws.dto.post.CreatePostRequest;
import safa.safepaws.dto.post.EditPostRequest;
import safa.safepaws.dto.post.GetPostResponse;
import safa.safepaws.dto.post.MapPostResponse;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.PostStatus;
import safa.safepaws.model.Address;
import safa.safepaws.model.Post;

import java.util.List;

@Mapper
public abstract class PostMapper {

    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    public abstract Post toEntity(CreatePostRequest createPostRequest);

    @Mapping(target = "type", source = "typeId", qualifiedByName = "toAnimalType")
    public abstract Post toEntity(EditPostRequest editPostRequest);

    @Mapping(target = "addressVillage", source = "address", qualifiedByName = "getLocation")
    @Mapping(target = "typeId", source = "type", qualifiedByName = "mapAnimalType")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapPostStatus")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "coordinateX", source = "address.coordinateX")
    @Mapping(target = "coordinateY", source = "address.coordinateY")
    public abstract GetPostResponse toDTO(Post post);
    public abstract List<GetPostResponse> toDTO(List<Post> postList);

    @Mapping(target = "latitude", source = "address.coordinateY")
    @Mapping(target = "longitude", source = "address.coordinateX")
    @Mapping(target = "postId", source = "id")
    public abstract MapPostResponse toMapDTO(Post post);
    public abstract List<MapPostResponse> toMapDTO(List<Post> postList);

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

    @Named("getLocation")
    public String getLocation(Address address) {
        if (address.getVillage() != null) {
            return address.getVillage();
        } else if (address.getProvince() != null) {
            return address.getProvince();
        } else {
            return address.getCountry();
        }
    }

}

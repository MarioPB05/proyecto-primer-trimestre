package safa.safepaws.mapper;

import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.request.RequestEditDTO;
import safa.safepaws.model.Address;
import safa.safepaws.model.Post;
import safa.safepaws.model.Request;

import java.util.List;

@Mapper
public abstract  class RequestMapper {


//    @Mapping(source = "post_id", target = "post", qualifiedByName = "conversorPostEntity")
//    public abstract Request toEntity(RequestCreateDTO requestdto);
//    public abstract Request toEntity(RequestEditDTO requestdto);

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "requestDate", source = "creationDate")
    @Mapping(target = "requestStatus", source = "status")
    @Mapping(target = "ownerName", source = "client.name")
    @Mapping(target = "applierName", source = "post.client.name")
    @Mapping(target = "postName", source = "post.name")
    @Mapping(target = "postPhoto", source = "post.photo")
    @Mapping(target = "addressVillage", source = "post.address.village")
    public abstract GetAdoptionsResponse toDTO(Request request);

//    public abstract List<RequestCreateDTO> toDTO(List<Request> requests);
//    public abstract List<Request> toEntity(List<RequestCreateDTO> requestdtos);


    public abstract List<GetAdoptionsResponse> toAdoptionsResponseDTO(List<Request> requestsList);

    @Named("getAddress")
    public String toAddress(Request request){
        return request.getPost().getAddress().getVillage();
    }


}

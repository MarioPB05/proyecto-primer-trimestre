package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.model.Request;

import java.util.List;

@Mapper
public abstract  class RequestMapper {

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "requestDate", source = "creationDate")
    @Mapping(target = "requestStatus", source = "status")
    @Mapping(target = "ownerName", source = "client.name")
    @Mapping(target = "applierName", source = "post.client.name")
    @Mapping(target = "postName", source = "post.name")
    @Mapping(target = "postPhoto", source = "post.photo")
    @Mapping(target = "addressVillage", source = "post.address.village")
    public abstract GetAdoptionsResponse toDTO(Request request);

    public abstract List<GetAdoptionsResponse> toAdoptionsResponseDTO(List<Request> requestsList);

}

package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.model.Request;
import safa.safepaws.service.ChatRoomService;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract  class RequestMapper {

    @Autowired
    private ChatRoomService chatRoomService;

    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "requestDate", source = "creationDate")
    @Mapping(target = "requestStatus", source = "status")
    @Mapping(target = "requestCode", source = "code")
    @Mapping(target = "ownerName", source = "client.name")
    @Mapping(target = "applierName", source = "post.client.name")
    @Mapping(target = "postName", source = "post.name")
    @Mapping(target = "postPhoto", source = "post.photo")
    @Mapping(target = "addressProvince", source = "post.address.province")
    @Mapping(target = "chatRoomCode", source = "post.id", qualifiedByName = "getChatRoomCode")
    public abstract GetAdoptionsResponse toDTO(Request request);

    public abstract List<GetAdoptionsResponse> toAdoptionsResponseDTO(List<Request> requestsList);

    @Named("getChatRoomCode")
    public String getChatRoomCode(Integer postId) {
        return chatRoomService.getChatRoomCode(postId);
    }

}

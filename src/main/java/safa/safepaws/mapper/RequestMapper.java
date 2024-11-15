package safa.safepaws.mapper;

import lombok.Data;
import org.mapstruct.Mapping;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.request.RequestEditDTO;
import safa.safepaws.model.Request;

import java.util.List;

@Data
public abstract  class RequestMapper {


    @Mapping(source = "post_id", target = "post", qualifiedByName = "conversorPostEntity")
    public abstract Request toEntity(RequestCreateDTO requestdto);
    public abstract Request toEntity(RequestEditDTO requestdto);

    @Mapping(source = "Post", target = "Post", qualifiedByName = "conversorPostEntity")
    public abstract RequestCreateDTO toDTO(Request request);

    public abstract List<RequestCreateDTO> toDTO(List<Request> requests);

    public abstract List<Request> toEntity(List<RequestCreateDTO> requestdtos);
}

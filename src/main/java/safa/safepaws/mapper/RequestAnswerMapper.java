package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import safa.safepaws.dto.requestAnswer.GetAnswersResponse;
import safa.safepaws.model.RequestAnswer;

import java.util.List;

@Mapper
public interface RequestAnswerMapper {
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "request.id", target = "requestId")
    GetAnswersResponse toDTO(RequestAnswer requestAnswer);
    List<GetAnswersResponse> toDTO(List<RequestAnswer> requestAnswersList);
}

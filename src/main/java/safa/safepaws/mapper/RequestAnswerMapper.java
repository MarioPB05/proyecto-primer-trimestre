package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import safa.safepaws.dto.requestAnswer.GetAnswersResponse;
import safa.safepaws.model.RequestAnswer;

import java.util.List;

@Mapper
public interface RequestAnswerMapper {
    @Mapping(source = "question.question", target = "question")
    @Mapping(source = "question.type", target = "questionType")
    @Mapping(source = "question.required", target = "required")
    GetAnswersResponse toDTO(RequestAnswer requestAnswer);
    List<GetAnswersResponse> toDTO(List<RequestAnswer> requestAnswersList);
}

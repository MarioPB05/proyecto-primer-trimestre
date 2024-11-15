package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import safa.safepaws.dto.question.QuestionCreateRequest;
import safa.safepaws.model.Question;

@Mapper
public interface QuestionMapper {
        QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

        @Mapping(source = "requiredQuestionId", target = "requiredQuestion.id")
        Question toEntity(QuestionCreateRequest dto);


}

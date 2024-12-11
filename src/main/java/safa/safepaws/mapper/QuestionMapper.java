package safa.safepaws.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import safa.safepaws.dto.question.QuestionCreateRequest;
import safa.safepaws.dto.question.QuestionResponse;
import safa.safepaws.enums.AnimalType;
import safa.safepaws.enums.QuestionType;
import safa.safepaws.model.Question;

@Mapper
public abstract class QuestionMapper {

        @Mapping(source = "requiredQuestionId", target = "requiredQuestion.id")
        public abstract Question toEntity(QuestionCreateRequest dto);

        @Mapping(source = "type", target = "type", qualifiedByName = "mapQuestionType")
        @Mapping(source = "requiredQuestion.id", target = "requiredQuestionId")
        @Mapping(source = "placeholder", target = "placeholder")
        public abstract QuestionResponse toResponse(Question entity);

        @Named("mapQuestionType")
        public Integer mapQuestionType(QuestionType questionType) {
                return questionType != null ? questionType.ordinal() : null;
        }

}

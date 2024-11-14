package safa.safepaws.dto.question;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.enums.QuestionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

        private String question;
        private QuestionType type;
        private Boolean required;
        private Integer requiredQuestionId;

}

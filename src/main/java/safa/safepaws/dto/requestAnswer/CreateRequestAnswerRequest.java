package safa.safepaws.dto.requestAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestAnswerRequest {
    private Integer questionId;
    private String answer;
}

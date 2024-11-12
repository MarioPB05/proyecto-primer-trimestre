package safa.safepaws.dto.requestAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAnswersResponse {
    private Integer id;
    private Integer questionId;
    private Integer requestId;
    private String answer;
}

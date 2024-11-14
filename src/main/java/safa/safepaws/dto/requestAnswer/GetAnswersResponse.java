package safa.safepaws.dto.requestAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAnswersResponse {
    private Integer id;
    private String question;
    private Integer questionType;
    private Boolean required;
    private String answer;
}

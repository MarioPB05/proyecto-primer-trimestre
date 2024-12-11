package safa.safepaws.dto.request;

import lombok.Data;
import safa.safepaws.dto.requestAnswer.CreateRequestAnswerRequest;

import java.util.List;

@Data
public class RequestCreateDTO {
    private String message;
    private Integer postId;
    private List<CreateRequestAnswerRequest> answers;
}

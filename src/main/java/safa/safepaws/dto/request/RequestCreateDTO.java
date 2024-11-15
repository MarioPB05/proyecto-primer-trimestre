package safa.safepaws.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestCreateDTO {

    private String message;
    private LocalDate creationDate;
    private Integer status;
    private Integer postId;
}

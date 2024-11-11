package safa.safepaws.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionResponse {
    private int status;
    private String message;
    private long timestamp;

    public ExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

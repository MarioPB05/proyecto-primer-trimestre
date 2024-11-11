package safa.safepaws.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getStatusCode().value(),
                ex.getReason()
        );
        return new ResponseEntity<>(exceptionResponse, ex.getStatusCode());
    }
}
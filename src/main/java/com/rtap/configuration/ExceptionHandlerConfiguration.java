package com.rtap.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        ProblemDetail problemDetail =
                constructProblemDetail(
                        HttpStatus.valueOf(status.value()),
                        ex.getClass().getSimpleName(),
                        "BadRequest",
                        errors,
                        request);
        return new ResponseEntity<>(problemDetail, status);
    }

    private ProblemDetail constructProblemDetail(
            HttpStatus status, String type, String title, Object detail, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setProperty("type", type);
        problemDetail.setProperty("title", title);
        problemDetail.setProperty("detail", detail);
        problemDetail.setProperty("timestamp", Instant.now());
        log.error("request={} caused problem={}", request, problemDetail);
        return problemDetail;
    }
}
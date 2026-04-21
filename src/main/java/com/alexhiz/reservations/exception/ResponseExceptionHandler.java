package com.alexhiz.reservations.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorTemplate> handleDefaultException(Exception ex, WebRequest request) {
        CustomErrorTemplate err = new CustomErrorTemplate(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorTemplate> handleModelNotFoundException(ModelNotFoundException ex,
            WebRequest request) {
        CustomErrorTemplate err = new CustomErrorTemplate(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    // Desde Spring Boot 3+
    /*
     * @ExceptionHandler(ModelNotFoundException.class)
     * public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex,
     * WebRequest request) {
     * ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
     * ex.getMessage());
     * pd.setTitle("Model Not Found");
     * pd.setType(URI.create(request.getDescription(false)));
     * pd.setProperty("extra1", "extra-value1");
     * pd.setProperty("extra2", 35);
     * return pd;
     * }
     */

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<CustomErrorTemplate> handleArithmeticException(ArithmeticException ex, WebRequest request) {
        CustomErrorTemplate err = new CustomErrorTemplate(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
    }

    /*
     * @ExceptionHandler(MethodArgumentNotValidException.class)
     * public ResponseEntity<CustomErrorTemplate>
     * handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
     * WebRequest request) {
     * String msg = ex.getBindingResult().getFieldErrors().stream()
     * .map(e -> e.getField().concat(": ").concat(e.getDefaultMessage()))
     * .collect(Collectors.joining(","));
     * 
     * CustomErrorTemplate err = new CustomErrorTemplate(LocalDateTime.now(), msg,
     * request.getDescription(false));
     * 
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
     * }
     */

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField().concat(": ").concat(e.getDefaultMessage()))
                .collect(Collectors.joining(","));

        CustomErrorTemplate err = new CustomErrorTemplate(LocalDateTime.now(), msg, request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}

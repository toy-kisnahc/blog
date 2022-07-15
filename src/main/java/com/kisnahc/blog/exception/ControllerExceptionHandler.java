package com.kisnahc.blog.exception;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * @NotBlank 검증 예외처리.
     * @param
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        ErrorResponse body = ErrorResponse.builder()
                .statusCode(SC_BAD_REQUEST)
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .reason(Map.of(e.getFieldError().getField(), Objects.requireNonNull(e.getFieldError().getRejectedValue()).toString()))
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * 어플리케이션 커스텀 예외처리.
     * @param e
     * @return
     */
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorResponse> blogExceptionHandler(BlogException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .statusCode(statusCode)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }
}

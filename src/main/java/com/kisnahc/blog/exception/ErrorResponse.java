package com.kisnahc.blog.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor()
@Getter
public class ErrorResponse {

    private int statusCode;
    private String message;
    private Map<String, String> reason = new HashMap<>();

    @Builder
    public ErrorResponse(int statusCode, String message, Map<String, String> reason) {
        this.statusCode = statusCode;
        this.message = message;
        this.reason = reason;
    }

}

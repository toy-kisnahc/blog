package com.kisnahc.blog.dto.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int statusCode;
    private T data;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}

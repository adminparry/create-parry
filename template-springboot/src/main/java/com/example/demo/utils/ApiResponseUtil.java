package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseUtil<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponseUtil<T> success(T data) {
        return new ApiResponseUtil<>(200, "Success", data);
    }

    public static <T> ApiResponseUtil<T> error(int code, String message) {
        return new ApiResponseUtil<>(code, message, null);
    }
} 
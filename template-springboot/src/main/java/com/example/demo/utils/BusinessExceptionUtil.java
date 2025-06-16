package com.example.demo.utils;

import lombok.Getter;

@Getter
public class BusinessExceptionUtil extends RuntimeException {
    private final int code;

    public BusinessExceptionUtil(String message) {
        this(500, message);
    }

    public BusinessExceptionUtil(int code, String message) {
        super(message);
        this.code = code;
    }
}
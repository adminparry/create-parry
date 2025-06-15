package com.example.demo.auth.exception;

import lombok.Getter;

@Getter
public class SsoException extends RuntimeException {

    private final int code;

    public SsoException(String message) {
        this(401, message);
    }

    public SsoException(int code, String message) {
        super(message);
        this.code = code;
    }
}

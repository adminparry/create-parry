package com.example.demo.auth.exception;

public class SsoException extends RuntimeException {

    public SsoException() {
    }

    public SsoException(String message) {
        super(message);
    }
}

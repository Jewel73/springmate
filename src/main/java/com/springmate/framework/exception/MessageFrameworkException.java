package com.springmate.framework.exception;

public class MessageFrameworkException extends RuntimeException {
    public MessageFrameworkException(String message) {
        super(message);
    }

    public MessageFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
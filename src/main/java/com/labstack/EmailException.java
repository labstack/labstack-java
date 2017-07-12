package com.labstack;

/**
 * Defines the email exception.
 */
public class EmailException extends RuntimeException {
    private int code;
    private String message;

    public EmailException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

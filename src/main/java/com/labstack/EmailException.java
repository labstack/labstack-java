package com.labstack;

/**
 * Defines the email exception.
 */
public class EmailException extends RuntimeException {
    private int code;

    public EmailException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

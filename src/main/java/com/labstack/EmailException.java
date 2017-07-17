package com.labstack;

/**
 * Defines the email exception.
 */
public class EmailException extends RuntimeException {
    private int code;
    private String message;

    public EmailException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("email error, code=%d, message=%s", code, message);
    }
}

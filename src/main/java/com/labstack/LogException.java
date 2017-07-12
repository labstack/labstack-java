package com.labstack;

/**
 * Defines the log exception.
 */
public class LogException extends RuntimeException {
    private int code;
    private String message;

    public LogException(int code, String message) {
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

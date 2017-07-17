package com.labstack;

/**
 * Defines the log exception.
 */
public class LogException extends RuntimeException {
    private int code;
    private String message;

    public LogException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("log error, code=%d, message=%s", code, message);
    }
}

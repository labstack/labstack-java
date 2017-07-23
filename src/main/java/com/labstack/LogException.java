package com.labstack;

/**
 * Defines the log exception.
 */
public class LogException extends RuntimeException {
    private int code;

    public LogException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

package com.labstack;

/**
 * Defines the log exception.
 */
public class ConnectException extends RuntimeException {
    private int code;

    public ConnectException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

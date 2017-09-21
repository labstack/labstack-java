package com.labstack;

/**
 * Defines the log exception.
 */
public class HubException extends RuntimeException {
    private int code;

    public HubException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

package com.labstack;

/**
 * Defines the LabStack exception.
 */
public class LabstackException extends RuntimeException {
    private int code;
    private String message;

    public LabstackException(int code, String message) {
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

package com.labstack;

/**
 * Defines the LabStack exception.
 */
public class LabstackException extends RuntimeException {
    private int code;

    public LabstackException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

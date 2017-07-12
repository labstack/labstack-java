package com.labstack;

/**
 * Defines the store exception.
 */
public class StoreException extends RuntimeException {
    private int code;
    private String message;

    public StoreException(int code, String message) {
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

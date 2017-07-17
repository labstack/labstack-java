package com.labstack;

/**
 * Defines the store exception.
 */
public class StoreException extends RuntimeException {
    private int code;
    private String message;

    public StoreException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("store error, code=%d, message=%s", code, message);
    }
}

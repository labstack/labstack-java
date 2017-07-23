package com.labstack;

/**
 * Defines the store exception.
 */
public class StoreException extends RuntimeException {
    private int code;

    public StoreException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

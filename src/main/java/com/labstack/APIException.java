package com.labstack;

/**
 * Defines the api exception.
 */
public class APIException extends RuntimeException {
    private int code;

    public APIException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

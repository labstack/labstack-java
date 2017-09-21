package com.labstack;

/**
 * Defines the email exception.
 */
public class JetException extends RuntimeException {
    private int code;

    public JetException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

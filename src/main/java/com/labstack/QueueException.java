package com.labstack;

/**
 * Defines the log exception.
 */
public class QueueException extends RuntimeException {
    private int code;

    public QueueException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

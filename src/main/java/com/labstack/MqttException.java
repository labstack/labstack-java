package com.labstack;

/**
 * Defines the log exception.
 */
public class MqttException extends RuntimeException {
    private int code;

    public MqttException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

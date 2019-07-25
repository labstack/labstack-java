package com.labstack;

public class LabstackException extends Exception {
    private int code;

    public LabstackException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

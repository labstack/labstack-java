package com.labstack;

public class LabstackException extends Exception {
    private Integer statusCode;
    private Integer code;

    public LabstackException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

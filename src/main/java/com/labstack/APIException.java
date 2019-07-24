package com.labstack;

public class APIException extends RuntimeException {
    public static final com.squareup.moshi.JsonAdapter<APIException> JsonAdapter = Client.MOSHI.adapter(
            APIException.class);
    private int code;

    public APIException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

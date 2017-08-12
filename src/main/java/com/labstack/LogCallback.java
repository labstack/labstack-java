package com.labstack;

public interface LogCallback {
    void onSuccess();

    void onError(LogException e);
}

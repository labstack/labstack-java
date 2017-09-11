package com.labstack;

public interface MessageDataHandler {
    void handle(String topic, byte[] data);
}

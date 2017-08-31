package com.labstack;

public interface ConnectMessageHandler {
    void handle(String topic, byte[] payload);
}

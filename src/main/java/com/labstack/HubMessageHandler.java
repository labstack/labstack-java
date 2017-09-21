package com.labstack;

public interface HubMessageHandler {
    void handle(String topic, byte[] data);
}

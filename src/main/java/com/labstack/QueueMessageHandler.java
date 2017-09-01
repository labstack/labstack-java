package com.labstack;

public interface QueueMessageHandler {
    void handle(String topic, byte[] payload);
}

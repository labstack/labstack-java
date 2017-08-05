package com.labstack;

public interface MqttMessageHandler {
    void handle(String topic, byte[] message);
}

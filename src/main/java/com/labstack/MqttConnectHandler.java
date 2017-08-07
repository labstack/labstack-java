package com.labstack;

public interface MqttConnectHandler {
    void handle(boolean reconnect, String serverURI);
}

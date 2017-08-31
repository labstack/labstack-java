package com.labstack;

public interface ConnectConnectionHandler {
    void handle(boolean reconnect, String serverURI);
}

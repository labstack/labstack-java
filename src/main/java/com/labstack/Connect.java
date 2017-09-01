package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Connect {
    private String accountId;
    private IMqttAsyncClient client;
    private ConnectConnectionHandler connectHandler;
    private ConnectMessageHandler messageHandler;

    protected Connect(String accountId, String apiKey, IMqttAsyncClient client) throws MqttException {
        this.accountId = accountId;
        this.client = client;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setUserName(accountId);
        options.setPassword(apiKey.toCharArray());
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                topic = topic.replace(Connect.this.accountId + "/", "");
                if (Connect.this.messageHandler != null) {
                    Connect.this.messageHandler.handle(topic, message.getPayload());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (Connect.this.connectHandler != null) {
                    Connect.this.connectHandler.handle();
                }
            }
        });
        client.connect(options);
    }

    public void onConnect(ConnectConnectionHandler handler) {
        connectHandler = handler;
    }

    public void onMessage(ConnectMessageHandler handler) {
        messageHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws ConnectException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (client.isConnected()) {
                client.publish(topic, new MqttMessage(payload));
            }
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }

    public void subscribe(String topic) {
        topic = String.format("%s/%s", accountId, topic);
        try {
            client.subscribe(topic, 0);
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws ConnectException {
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }
}

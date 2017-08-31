package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Connect {
    private String accountId;
    private IMqttAsyncClient mqttClient;
    private ConnectMessageHandler messageHandler;
    private ConnectConnectionHandler connectHandler;

    protected Connect(String accountId, String apiKey, IMqttAsyncClient mqttClient) throws MqttException {
        this.accountId = accountId;
        this.mqttClient = mqttClient;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setUserName(accountId);
        options.setPassword(apiKey.toCharArray());
        mqttClient.setCallback(new MqttCallbackExtended() {
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
                    Connect.this.connectHandler.handle(reconnect, serverURI);
                }
            }
        });
        mqttClient.connect(options);
    }

    public void onMessage(ConnectMessageHandler handler) {
        messageHandler = handler;
    }

    public void onConnect(ConnectConnectionHandler handler) {
        connectHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws ConnectException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (mqttClient.isConnected()) {
                mqttClient.publish(topic, new MqttMessage(payload));
            }
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }

    public void subscribe(String topic) {
        topic = String.format("%s/%s", accountId, topic);
        try {
            mqttClient.subscribe(topic, 0);
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws ConnectException {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }
}

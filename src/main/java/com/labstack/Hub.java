package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Hub {
    private String accountId;
    private IMqttAsyncClient client;
    private HubConnectHandler connectHandler;
    private HubMessageHandler messageHandler;

    protected Hub(String accountId, String apiKey, IMqttAsyncClient client) throws MqttException {
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
                topic = topic.replace(Hub.this.accountId + "/", "");
                if (Hub.this.messageHandler != null) {
                    Hub.this.messageHandler.handle(topic, message.getPayload());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (Hub.this.connectHandler != null) {
                    Hub.this.connectHandler.handle();
                }
            }
        });
        client.connect(options);
    }

    public void onConnect(HubConnectHandler handler) {
        connectHandler = handler;
    }

    public void onMessage(HubMessageHandler handler) {
        messageHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws HubException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (client.isConnected()) {
                client.publish(topic, new MqttMessage(payload));
            }
        } catch (MqttException e) {
            throw new HubException(e.getReasonCode(), e.getMessage());
        }
    }

    public void subscribe(String topic, boolean shared) {
        topic = String.format("%s/%s", accountId, topic);
        if (shared) {
            topic = "$queue/" + topic;
        }
        try {
            client.subscribe(topic, 0);
        } catch (MqttException e) {
            throw new HubException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws HubException {
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new HubException(e.getReasonCode(), e.getMessage());
        }
    }
}

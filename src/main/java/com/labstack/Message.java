package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Message {
    private String accountId;
    private IMqttAsyncClient client;
    private MessageConnectHandler connectHandler;
    private MessageDataHandler messageHandler;

    protected Message(String accountId, String apiKey, IMqttAsyncClient client) throws MqttException {
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
                topic = topic.replace(Message.this.accountId + "/", "");
                if (Message.this.messageHandler != null) {
                    Message.this.messageHandler.handle(topic, message.getPayload());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (Message.this.connectHandler != null) {
                    Message.this.connectHandler.handle();
                }
            }
        });
        client.connect(options);
    }

    public void onConnect(MessageConnectHandler handler) {
        connectHandler = handler;
    }

    public void onMessage(MessageDataHandler handler) {
        messageHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws MessageException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (client.isConnected()) {
                client.publish(topic, new MqttMessage(payload));
            }
        } catch (MqttException e) {
            throw new MessageException(e.getReasonCode(), e.getMessage());
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
            throw new MessageException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws MessageException {
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new MessageException(e.getReasonCode(), e.getMessage());
        }
    }
}

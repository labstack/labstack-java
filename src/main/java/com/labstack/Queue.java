package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Queue {
    private String accountId;
    private IMqttAsyncClient client;
    private QueueConnectHandler connectHandler;
    private QueueMessageHandler messageHandler;

    protected Queue(String accountId, String apiKey, IMqttAsyncClient client) throws MqttException {
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
                topic = topic.replace(Queue.this.accountId + "/", "");
                if (Queue.this.messageHandler != null) {
                    Queue.this.messageHandler.handle(topic, message.getPayload());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (Queue.this.connectHandler != null) {
                    Queue.this.connectHandler.handle();
                }
            }
        });
        client.connect(options);
    }

    public void onConnect(QueueConnectHandler handler) {
        connectHandler = handler;
    }

    public void onMessage(QueueMessageHandler handler) {
        messageHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws QueueException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (client.isConnected()) {
                client.publish(topic, new MqttMessage(payload));
            }
        } catch (MqttException e) {
            throw new QueueException(e.getReasonCode(), e.getMessage());
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
            throw new QueueException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws QueueException {
        try {
            client.disconnect();
        } catch (MqttException e) {
            throw new QueueException(e.getReasonCode(), e.getMessage());
        }
    }
}

package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Mqtt {
    private String accountId;
    private IMqttAsyncClient client;
    private MqttMessageHandler messageHandler;
    private MqttConnectHandler connectHandler;

    protected Mqtt(String accountId, String apiKey, String clientId, IMqttAsyncClient client) throws org.eclipse.paho.client.mqttv3.MqttException {
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
                topic = topic.replace(Mqtt.this.accountId + "/", "");
                if (Mqtt.this.messageHandler != null) {
                    Mqtt.this.messageHandler.handle(topic, message.getPayload());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (Mqtt.this.connectHandler != null) {
                    Mqtt.this.connectHandler.handle(reconnect, serverURI);
                }
            }
        });
        client.connect(options);
    }

    public void onMessage(MqttMessageHandler handler) {
        messageHandler = handler;
    }

    public void onConnect(MqttConnectHandler handler) {
        connectHandler = handler;
    }

    public void publish(String topic, byte[] payload) throws MqttException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (client.isConnected()) {
                client.publish(topic, new MqttMessage(payload));
            }
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }

    public void subscribe(String topic) {
        topic = String.format("%s/%s", accountId, topic);
        try {
            client.subscribe(topic, 0);
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }

    public void disconnect() throws MqttException {
        try {
            client.disconnect();
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }
}

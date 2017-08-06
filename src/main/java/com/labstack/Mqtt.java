package com.labstack;

import org.eclipse.paho.client.mqttv3.*;

public class Mqtt {
    private String accountId;
    private MqttMessageHandler handler;

    protected MqttAsyncClient mqtt;

    protected Mqtt(String accountId, String apiKey, String clientId) {
        this.accountId = accountId;
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setUserName(accountId);
        options.setPassword(apiKey.toCharArray());
        try {
            mqtt = new MqttAsyncClient(Client.MQTT_BROKER, clientId, null);
            mqtt.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    topic = topic.replace(Mqtt.this.accountId + "/", "");
                    Mqtt.this.handler.handle(topic, message.getPayload());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            IMqttToken token = mqtt.connect(options);
            token.waitForCompletion();
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }

    public void publish(String topic, byte[] payload) throws MqttException {
        try {
            topic = String.format("%s/%s", accountId, topic);
            if (mqtt.isConnected()) {
                mqtt.publish(topic, new MqttMessage(payload));
            }
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }

    public void subscribe(String topic) {
        topic = String.format("%s/%s", accountId, topic);
        try {
            mqtt.subscribe(topic, 0);
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }

    public void onMessage(MqttMessageHandler handler) {
        this.handler = handler;
    };

    public void disconnect() throws MqttException {
        try {
            mqtt.disconnect();
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
        }
    }
}

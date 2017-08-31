package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.Date;

public class Client {
    protected String accountId;
    protected String apiKey;
    protected OkHttpClient okHttp;
    protected static Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
    protected static JsonAdapter<SearchParameters> paramsJsonAdapter = moshi.adapter(SearchParameters.class);
    public static final String API_URL = "https://api.labstack.com";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    // public static final String MQTT_BROKER = "ssl://iot.labstack.com:8883";
    public static final String MQTT_BROKER = "tcp://iot.labstack.com:1883";

    public Client(String accountId, String apiKey) {
        this.accountId = accountId;
        this.apiKey = apiKey;
        okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    /**
     * @return Email service.
     */
    public Email email() {
        return new Email(okHttp);
    }

    /**
     * @return Log service.
     */
    public Log log() {
        return new Log(this);
    }

    public Connect connect(String clientId) throws ConnectException {
        try {
            IMqttAsyncClient mqttClient = new MqttAsyncClient(Client.MQTT_BROKER, clientId);
            return new Connect(this.accountId, this.apiKey, mqttClient);
        } catch (MqttException e) {
            throw new ConnectException(e.getReasonCode(), e.getMessage());
        }
    }

    /**
     * @return Store service.
     */
    public Store store() {
        Store store = new Store();
        store.okHttp = okHttp;
        return store;
    }
}

class Interceptor implements okhttp3.Interceptor {
    private String apiKey;

    public Interceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
        Request compressedRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return chain.proceed(compressedRequest);
    }
}


package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private String accountId;
    private String apiKey;
    private OkHttpClient okHttp;

    protected static Moshi moshi = new Moshi.Builder().build();
    protected static JsonAdapter<SearchParameters> paramsJsonAdapter = moshi.adapter(SearchParameters.class);
    // TODO: We could have used `yyyy-MM-dd'T'HH:mm:ss.SSSXXX` but on Android 6 and below it doesn't work.
    // Remove when labstack-android is released.
    protected static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") {
        @Override
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
            StringBuffer rfcFormat = super.format(date, toAppendTo, pos);
            return rfcFormat.insert(rfcFormat.length() - 2, ":");
        }

        @Override
        public Date parse(String text, ParsePosition pos) {
            if (text.length() > 3) {
                text = text.substring(0, text.length() - 3) + text.substring(text.length() - 2);
            }
            return super.parse(text, pos);
        }
    };

    public static final String API_URL = "https://api.labstack.com";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    //    public static final String MQTT_BROKER = "ssl://iot.labstack.com:8883";
    public static final String MQTT_BROKER = "tcp://iot.labstack.com:1883";

    public Client(String accountId, String apiKey) {
        this.accountId = accountId;
        this.apiKey = apiKey;
        okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getApiKey() {
        return apiKey;
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
        return new Log(okHttp);
    }

    public Mqtt mqtt(String clientId) throws MqttException {
        try {
            IMqttAsyncClient client = new MqttAsyncClient(Client.MQTT_BROKER, clientId, null);
            return new Mqtt(accountId, apiKey, clientId, client);
        } catch (org.eclipse.paho.client.mqttv3.MqttException e) {
            throw new MqttException(e.getReasonCode(), e.getMessage());
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


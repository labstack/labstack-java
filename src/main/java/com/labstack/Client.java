package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Client {
    private String accountId;
    private String apiKey;
    private OkHttpClient okHttp;

    protected static Moshi moshi = new Moshi.Builder().build();
    protected static JsonAdapter<SearchParameters> paramsJsonAdapter = moshi.adapter(SearchParameters.class);
    protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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

    /**
     * @return Email service.
     */
    public Email Email() {
        return new Email(okHttp);
    }

    /**
     * @return Log service.
     */
    public Log Log() {
        Log log = new Log();
        log.okHttp = okHttp;
        log.setLevel(Level.INFO);
        log.setBatchSize(60);
        log.setDispatchInterval(60);
        return log;
    }

    public Mqtt Mqtt(String clientId) throws MqttException {
        return new Mqtt(accountId, apiKey, clientId);
    }

    /**
     * @return Store service.
     */
    public Store Store() {
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


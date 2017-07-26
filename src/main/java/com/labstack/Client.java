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
    private OkHttpClient okHttp;
    private String appId;
    private String appName;
    protected static Moshi moshi = new Moshi.Builder().build();
    protected static JsonAdapter<SearchParameters> paramsJsonAdapter = moshi.adapter(SearchParameters.class);
    protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static final String API_URL = "https://api.labstack.com";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public Client(String apiKey) {
        okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return Email service.
     */
    public Email Email() {
        Email email = new Email();
        email.okHttp = okHttp;
        return email;
    }

    /**
     * @return Log service.
     */
    public Log Log() {
        Log log = new Log();
        log.okHttp = okHttp;
        log.setAppId(appId);
        log.setAppName(appName);
        log.setLevel(Level.INFO);
        log.setBatchSize(60);
        log.setDispatchInterval(60);
        return log;
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


package com.labstack;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Client {
    private OkHttpClient okHttp;
    private String appId;
    private String appName;

    public static final String API_URL = "https://api.labstack.com";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public Client(String apiKey) {
         okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request compressedRequest = chain.request().newBuilder()
                                .header("Authorization", "Bearer " + apiKey)
                                .build();
                        return chain.proceed(compressedRequest);
                    }
                })
                .build();
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Logging Logging() {
        Logging logging = new Logging();
        logging.setOkHttp(okHttp);
        logging.setAppId(appId);
        logging.setAppName(appName);
        logging.setLevel(Logging.INFO);
        logging.setBatchSize(60);
        logging.setDispatchInterval(60);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, TimeUnit.SECONDS.toMillis(logging.getDispatchInterval()));
        return logging;
    }
}

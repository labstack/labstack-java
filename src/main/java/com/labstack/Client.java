package com.labstack;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;


public class Client {
    public static final Moshi MOSHI = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .build();

    public Client(String key) {
    }

    public static OkHttpClient okHttpClient(String key) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(key))
                .build();
    }
}

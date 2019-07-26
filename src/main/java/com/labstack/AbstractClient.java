package com.labstack;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;


public abstract class AbstractClient {
    protected OkHttpClient okHttp;
    protected final Moshi MOSHI = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .build();
    protected final JsonAdapter<LabStackException> EXCEPTION_JSON_ADAPTER = MOSHI.adapter(LabStackException.class);

    protected AbstractClient(String key) {
        okHttp = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(key))
                .build();
    }
}

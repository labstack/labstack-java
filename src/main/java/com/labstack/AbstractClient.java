package com.labstack;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;


public abstract class AbstractClient {
    protected OkHttpClient okHttp;
    protected final Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .build();
    protected final JsonAdapter<LabstackException> exceptionJsonAdapter = moshi.adapter(LabstackException.class);

    protected AbstractClient(String key) {
        okHttp = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(key))
                .build();
    }
}

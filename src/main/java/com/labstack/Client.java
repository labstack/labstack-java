package com.labstack;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;


public class Client {
    static OkHttpClient okHttp;
    static final Moshi MOSHI = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .build();
    static final JsonAdapter<LabStackException> EXCEPTION_JSON_ADAPTER =
            MOSHI.adapter(LabStackException.class);

    public Client(String key) {
        okHttp = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(key))
                .build();
    }

    public CurrencyService currency() {
        return new CurrencyService();
    }

    public DomainService domain() {
        return new DomainService();
    }

    public EmailService email() {
        return new EmailService();
    }

    public IPService ip() {
        return new IPService();
    }

    public WebpageService webpage() {
        return new WebpageService();
    }
}

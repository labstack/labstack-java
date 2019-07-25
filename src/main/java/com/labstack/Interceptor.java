package com.labstack;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import okhttp3.Request;
import okhttp3.Response;


public class Interceptor implements okhttp3.Interceptor {
    private String key;

    public Interceptor(String key) {
        this.key = key;
    }

    @NotNull @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("Authorization", "Bearer " + key)
                .build();
        return chain.proceed(request);
    }
}

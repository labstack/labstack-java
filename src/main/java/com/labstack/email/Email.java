package com.labstack.email;

import java.io.IOException;
import com.labstack.APIException;
import com.labstack.Client;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Email {
    private static final String URL = "https://email.labstack.com/api/v1";
    private OkHttpClient okHttp;

    public Email(String key) {
        okHttp = Client.okHttpClient(key);
    }

    public Verify.Response verify(Verify.Request request) {
        HttpUrl.Builder builder = HttpUrl.parse(URL + "/verify").newBuilder();
        builder.addPathSegment(request.getEmail());
        Request req = new Request.Builder().url(builder.build()).build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return Verify.responseJsonAdapter.fromJson(res.body().source());
            }
            throw APIException.JsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }
}

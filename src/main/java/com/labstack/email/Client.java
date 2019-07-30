package com.labstack.email;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private final String URL = "https://email.labstack.com/api/v1";
    private final JsonAdapter<VerifyResponse> VERIFY_RESPONSE_JSON_ADAPTER = MOSHI.adapter(VerifyResponse.class);

    public Client(String key) {
        super(key);
    }

    public VerifyResponse verify(VerifyRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("verify")
                .addPathSegment(request.getEmail())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return VERIFY_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

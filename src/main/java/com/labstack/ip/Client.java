package com.labstack.ip;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private final String URL = "https://ip.labstack.com/api/v1";
    private final JsonAdapter<LookupResponse> LOOKUP_RESPONSE_JSON_ADAPTER = MOSHI.adapter(LookupResponse.class);

    public Client(String key) {
        super(key);
    }

    public LookupResponse lookup(LookupRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment(request.getIp())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return LOOKUP_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

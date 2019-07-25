package com.labstack.ip;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabstackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private static final String URL = "https://ip.labstack.com/api/v1";
    private final JsonAdapter<LookupResponse> lookupResponseJsonAdapter = moshi.adapter(LookupResponse.class);

    public Client(String key) {
        super(key);
    }

    public LookupResponse lookup(LookupRequest request) throws LabstackException {
        Request req = new Request.Builder()
                .url(String.format("%s/%s", URL, request.getIp()))
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(res.body().source());
            }
            return lookupResponseJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new LabstackException(0, e.getMessage());
        }
    }
}

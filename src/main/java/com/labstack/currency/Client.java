package com.labstack.currency;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabstackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private static final String URL = "https://currency.labstack.com/api/v1";
    private final JsonAdapter<ConvertResponse> convertResponseJsonAdapter = moshi.adapter(ConvertResponse.class);

    public Client(String key) {
        super(key);
    }

    public ConvertResponse convert(ConvertRequest request) throws LabstackException {
        Request req = new Request.Builder()
                .url(String.format("%s/convert/%s/%s/%s", URL, request.getAmount(), request.getFrom(), request.getTo()))
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(res.body().source());
            }
            return convertResponseJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabstackException.builder().message(e.getMessage()).build();
        }
    }
}

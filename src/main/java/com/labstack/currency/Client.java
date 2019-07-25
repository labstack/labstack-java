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

    public ConvertResponse convert(double amount, String from, String to) throws LabstackException {
        Request request = new Request.Builder()
                .url(String.format("%s/convert/%s/%s/%s", URL, amount, from, to))
                .build();
        try (Response response = okHttp.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
            return convertResponseJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new LabstackException(0, e.getMessage());
        }
    }
}

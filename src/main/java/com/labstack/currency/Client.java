package com.labstack.currency;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private final String URL = "https://currency.labstack.com/api/v1";
    private final JsonAdapter<ConvertResponse> convertResponseJsonAdapter = MOSHI.adapter(ConvertResponse.class);
    private final JsonAdapter<ListResponse> listResponseJsonAdapter = MOSHI.adapter(ListResponse.class);

    public Client(String key) {
        super(key);
    }

    public ConvertResponse convert(ConvertRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("convert")
                .addPathSegment(request.getAmount().toString())
                .addPathSegment(request.getFrom())
                .addPathSegment(request.getTo())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return convertResponseJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public ListResponse list(ListRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("list")
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return listResponseJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

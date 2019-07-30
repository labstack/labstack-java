package com.labstack.currency;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class CurrencyClient extends AbstractClient {
    private final String URL = "https://currency.labstack.com/api/v1";
    private final JsonAdapter<CurrencyConvertResponse> convertResponseJsonAdapter = MOSHI.adapter(
            CurrencyConvertResponse.class);
    private final JsonAdapter<CurrencyListResponse> listResponseJsonAdapter = MOSHI.adapter(CurrencyListResponse.class);

    public CurrencyClient(String key) {
        super(key);
    }

    public CurrencyConvertResponse convert(CurrencyConvertRequest request) throws LabStackException {
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

    public CurrencyListResponse list(CurrencyListRequest request) throws LabStackException {
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

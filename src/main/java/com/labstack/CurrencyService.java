package com.labstack;

import static com.labstack.Client.EXCEPTION_JSON_ADAPTER;
import static com.labstack.Client.MOSHI;
import static com.labstack.Client.okHttp;
import java.io.IOException;
import com.labstack.currency.ConvertRequest;
import com.labstack.currency.ConvertResponse;
import com.labstack.currency.ListRequest;
import com.labstack.currency.ListResponse;
import com.labstack.currency.RatesRequest;
import com.labstack.currency.RatesResponse;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class CurrencyService {
    private final String URL = "https://currency.labstack.com/api/v1";
    private final JsonAdapter<ConvertResponse> CONVERT_RESPONSE_JSON_ADAPTER = MOSHI.adapter(ConvertResponse.class);
    private final JsonAdapter<ListResponse> LIST_RESPONSE_JSON_ADAPTER = MOSHI.adapter(ListResponse.class);
    private final JsonAdapter<RatesResponse> RATES_RESPONSE_JSON_ADAPTER = MOSHI.adapter(RatesResponse.class);

    CurrencyService() {
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
            return CONVERT_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
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
            return LIST_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public RatesResponse rates(RatesRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("rates")
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return RATES_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

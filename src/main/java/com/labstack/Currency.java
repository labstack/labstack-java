package com.labstack;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.labstack.Client.*;

@SuppressWarnings("Duplicates")
public class Currency {
    private Client client;
    private JsonAdapter<ConvertResponse> convertResponseJsonAdapter = MOSHI.adapter(ConvertResponse.class);
    private JsonAdapter<RatesResponse> ratesResponseJsonAdapter = MOSHI.adapter(RatesResponse.class);

    public Currency(Client client) {
        this.client = client;
    }

    public static class ConvertResponse {
        private double value;
        @Json(name = "updated_at")
        private Date updatedAt;

        public double getValue() {
            return value;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class RatesResponse {
        private Map<String, Double> rates;
        @Json(name = "updated_at")
        private Date updatedAt;

        public Map<String, Double> getRates() {
            return rates;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }
    }

    public ConvertResponse convert(String from, String to, double value) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/currency/convert").newBuilder();
        httpBuider.addQueryParameter("from", from);
        httpBuider.addQueryParameter("to", to);
        httpBuider.addQueryParameter("value", String.valueOf(value));
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return convertResponseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public RatesResponse rates(String base) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/currency/rates").newBuilder();
        httpBuider.addQueryParameter("base", base);
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return ratesResponseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }
}

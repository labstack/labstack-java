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

    public static class ConvertRequest {
        private String from;
        private String to;
        private double value;

        public String getFrom() {
            return from;
        }

        public ConvertRequest setFrom(String from) {
            this.from = from;
            return this;
        }

        public String getTo() {
            return to;
        }

        public ConvertRequest setTo(String to) {
            this.to = to;
            return this;
        }

        public double getValue() {
            return value;
        }

        public ConvertRequest setValue(double value) {
            this.value = value;
            return this;
        }
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

    public static class RatesRequest {
        private String base;

        public String getBase() {
            return base;
        }

        public RatesRequest setBase(String base) {
            this.base = base;
            return this;
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

    public ConvertResponse convert(ConvertRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/currency/convert").newBuilder();
        httpBuider.addQueryParameter("from", request.getFrom());
        httpBuider.addQueryParameter("to", String.valueOf(request.getTo()));
        httpBuider.addQueryParameter("value", String.valueOf(request.getValue()));
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

    public RatesResponse rates(RatesRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/currency/rates").newBuilder();
        httpBuider.addQueryParameter("base", request.getBase());
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

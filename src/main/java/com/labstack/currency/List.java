package com.labstack.currency;

import static com.labstack.Client.MOSHI;
import com.labstack.Interceptor;
import com.squareup.moshi.JsonAdapter;


public class List {
    static final JsonAdapter<Response> responseJsonAdapter = MOSHI.adapter(List.Response.class);

    static class Request {
    }

    static class Response {
        java.util.List<Interceptor.Currency> currencies;

        java.util.List<Interceptor.Currency> getCurrencies() {
            return currencies;
        }

        Response setCurrencies(final java.util.List<Interceptor.Currency> currencies) {
            this.currencies = currencies;
            return this;
        }
    }
}

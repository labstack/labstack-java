package com.labstack;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.Map;

public class Currency {
    public static class ExchangeRequest {
        private String base;

        public ExchangeRequest setBase(String base) {
            this.base = base;
            return this;
        }
    }

    public static class ExchangeResponse extends Response {
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
}

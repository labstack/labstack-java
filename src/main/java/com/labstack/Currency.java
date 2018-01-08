package com.labstack;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.Map;

public class Currency {
    public static class ConvertRequest {
        private String base;

        public ConvertRequest setBase(String base) {
            this.base = base;
            return this;
        }
    }

    public static class ConvertResponse {
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

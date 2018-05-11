package com.labstack;

import com.squareup.moshi.FromJson;

import java.io.IOException;
import java.util.Map;

public class Properties {
    private Map<String, Object> map;

    private Properties(Map<String, Object> map) {
        this.map = map;
    }

    public Boolean getBoolean(String key) {
        Object value = map.get(key);
        if (value != null) {
            return (Boolean) value;
        }
        return false;
    }

    public Double getDouble(String key) {
        Object value = map.get(key);
        if (value != null) {
            return (Double) value;
        }
        return 0d;
    }

    public Integer getInt(String key) {
        return getDouble(key).intValue();
    }

    public Long getLong(String key) {
        return getDouble(key).longValue();
    }

    public String getString(String key) {
        Object value = map.get(key);
        if (value != null) {
            return (String) value;
        }
        return "";
    }

    static class JsonAdapter {
        @FromJson
        Properties fromJson(Map<String, Object> map) throws IOException {
            return new Properties(map);
        }
    }
}

package com.labstack;

import java.util.HashMap;
import java.util.Map;

public class Fields {
    protected Map<String, Object> data = new HashMap<>();

    public Fields add(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return data.get(key);
    }

    // TODO: More public access methods http://mongodb.github.io/mongo-java-driver/3.4/javadoc/org/bson/Document.html
}

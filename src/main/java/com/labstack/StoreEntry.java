package com.labstack;

import com.squareup.moshi.Json;
import com.sun.istack.internal.Nullable;

import java.util.Date;

/**
 * Defines a store entry
*/
public class StoreEntry {
    private String key;
    private Object value;
    @Json(name = "created_at")
    private Date createdAt;
    @Json(name = "updated_at")
    private Date updatedAt;

    protected StoreEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
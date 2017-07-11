package com.labstack;

/**
 * Defines a store entry
*/
public class StoreEntry {
    private String key;
    private Object value;
//    @Json(name = "created_at")
//    private Date updatedAt;
//    @Json(name = "updated_at")
//    private Date createdAt;

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
}
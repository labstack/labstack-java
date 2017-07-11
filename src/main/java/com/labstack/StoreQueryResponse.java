package com.labstack;

import java.util.List;

/**
 * Defines the store query response.
 */
public class StoreQueryResponse {
    private long total;
    private List<StoreEntry> entries;

    public long getTotal() {
        return total;
    }

    public List<StoreEntry> getEntries() {
        return entries;
    }
}

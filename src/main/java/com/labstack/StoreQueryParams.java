package com.labstack;

/**
 * Defines the query parameters for find entries.
 */
public class StoreQueryParams {
    private String filters;
    private int limit;
    private int offset;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}



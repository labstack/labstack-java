package com.labstack;

import java.util.List;

public class SearchParameters {
    String query;
    String queryString;
    String since;
    List<String> sort;
    int size;
    int from;

    public SearchParameters setQuery(String query) {
        this.query = query;
        return this;
    }

    public SearchParameters setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public SearchParameters setSince(String since) {
        this.since = since;
        return this;
    }

    public SearchParameters setSort(List<String> sort) {
        this.sort = sort;
        return this;
    }

    public SearchParameters setSize(int size) {
        this.size = size;
        return this;
    }

    public SearchParameters setFrom(int from) {
        this.from = from;
        return this;
    }
}

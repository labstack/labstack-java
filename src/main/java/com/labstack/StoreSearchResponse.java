package com.labstack;

import java.util.List;

public class StoreSearchResponse {
    private long total;
    private List<Document> documents;

    public long getTotal() {
        return total;
    }

    public List<Document> getDocuments() {
        return documents;
    }
}

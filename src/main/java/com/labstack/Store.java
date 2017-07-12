package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.*;

import java.io.IOException;
import java.util.Date;

/**
 * Defines the LabStack store service.
 */
@SuppressWarnings("Duplicates")
public final class Store {
    protected OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe()).build();
    private JsonAdapter<StoreEntry> entryJsonAdapter = moshi.adapter(StoreEntry.class);
    private JsonAdapter<StoreQueryResponse> queryResponseJsonAdapter = moshi.adapter(StoreQueryResponse.class);
    private JsonAdapter<StoreException> exceptionJsonAdapter = moshi.adapter(StoreException.class);

    protected Store() {
    }

    public StoreEntry insert(String key, Object value) throws StoreException {
        StoreEntry entry = new StoreEntry(key, value);
        String json = entryJsonAdapter.toJson(entry);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store")
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() == 201) {
                return entryJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public StoreEntry get(String key) throws StoreException {
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store/" + key)
                .get()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() == 200) {
                return entryJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public StoreQueryResponse query() throws StoreException {
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store")
                .get()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() == 200) {
                return queryResponseJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public StoreQueryResponse query(StoreQueryParams params) throws Exception {
        String url = HttpUrl.parse(Client.API_URL + "/store").newBuilder()
                .addQueryParameter("filters", params.getFilters())
                .addQueryParameter("limit", Integer.toString(params.getLimit()))
                .addQueryParameter("offset", Integer.toString(params.getOffset()))
                .build().toString();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() == 200) {
                return queryResponseJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public void update(String key, Object value) throws StoreException {
        StoreEntry entry = new StoreEntry(key, value);
        String json = entryJsonAdapter.toJson(entry);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store/" + key)
                .put(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() != 204) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public void delete(String key) throws Exception {
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store/" + key)
                .delete()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.code() != 204) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }
}



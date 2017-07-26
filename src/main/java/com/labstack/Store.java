package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Defines the LabStack store service.
 */
@SuppressWarnings("Duplicates")
public final class Store {
    protected OkHttpClient okHttp;
    private JsonAdapter<Map<String, Object>> documentJsonAdapter = Client.moshi.adapter(Types.newParameterizedType(Map.class, String.class, Object.class));
    private JsonAdapter<StoreSearchResponse> searchResponseJsonAdapter = Client.moshi.adapter(StoreSearchResponse.class);
    private JsonAdapter<StoreException> exceptionJsonAdapter = Client.moshi.adapter(StoreException.class);

    protected Store() {
    }

    public Document insert(String collection, Document document) throws StoreException {
        String json = documentJsonAdapter.toJson(document.data);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/store/" + collection)
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                document.data = documentJsonAdapter.fromJson(response.body().source());
                return document;
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public Document get(String collection, String id) throws StoreException {
        Request request = new Request.Builder()
                .url(String.format("%s/store/%s/%s", Client.API_URL, collection, id))
                .get()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                Document document = new Document();
                document.data = documentJsonAdapter.fromJson(response.body().source());
                return document;
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public StoreSearchResponse search(String collection, SearchParameters parameters) throws StoreException {
        String json = Client.paramsJsonAdapter.toJson(parameters);
        Request request = new Request.Builder()
                .url(String.format("%s/store/%s/search", Client.API_URL, collection))
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                return searchResponseJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public void update(String collection, String id, Document document) throws StoreException {
        String json = documentJsonAdapter.toJson(document.data);
        Request request = new Request.Builder()
                .url(String.format("%s/store/%s/%s", Client.API_URL, collection, id))
                .patch(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }

    public void delete(String collection, String id) throws Exception {
        Request request = new Request.Builder()
                .url(String.format("%s/store/%s/%s", Client.API_URL, collection, id))
                .delete()
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
        } catch (IOException e) {
            throw new StoreException(0, e.getMessage());
        }
    }
}



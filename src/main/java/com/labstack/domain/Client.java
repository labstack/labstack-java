package com.labstack.domain;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private final String URL = "https://domain.labstack.com/api/v1";
    private final JsonAdapter<DNSResponse> DNS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(DNSResponse.class);
    private final JsonAdapter<SearchResponse> SEARCH_RESPONSE_JSON_ADAPTER = MOSHI.adapter(SearchResponse.class);
    private final JsonAdapter<StatusResponse> STATUS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(StatusResponse.class);
    private final JsonAdapter<WhoisResponse> WHOIS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(WhoisResponse.class);

    public Client(String key) {
        super(key);
    }

    public DNSResponse dns(DNSRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment(request.getType())
                .addPathSegment(request.getDomain())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return DNS_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public SearchResponse search(SearchRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("search")
                .addPathSegment(request.getDomain())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return SEARCH_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public StatusResponse status(StatusRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("status")
                .addPathSegment(request.getDomain())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return STATUS_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public WhoisResponse whois(WhoisRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("whois")
                .addPathSegment(request.getDomain())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return WHOIS_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

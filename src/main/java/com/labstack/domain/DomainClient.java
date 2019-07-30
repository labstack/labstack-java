package com.labstack.domain;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class DomainClient extends AbstractClient {
    private final String URL = "https://domain.labstack.com/api/v1";
    private final JsonAdapter<DomainDNSResponse> DNS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(DomainDNSResponse.class);
    private final JsonAdapter<DomainSearchResponse> SEARCH_RESPONSE_JSON_ADAPTER = MOSHI.adapter(DomainSearchResponse.class);
    private final JsonAdapter<DomainStatusResponse> STATUS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(DomainStatusResponse.class);
    private final JsonAdapter<DomainWhoisResponse> WHOIS_RESPONSE_JSON_ADAPTER = MOSHI.adapter(DomainWhoisResponse.class);

    public DomainClient(String key) {
        super(key);
    }

    public DomainDNSResponse dns(DomainDNSRequest request) throws LabStackException {
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

    public DomainSearchResponse search(DomainSearchRequest request) throws LabStackException {
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

    public DomainStatusResponse status(DomainStatusRequest request) throws LabStackException {
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

    public DomainWhoisResponse whois(DomainWhoisRequest request) throws LabStackException {
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

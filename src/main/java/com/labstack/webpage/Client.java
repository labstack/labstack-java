package com.labstack.webpage;

import java.io.IOException;
import com.labstack.AbstractClient;
import com.labstack.LabStackException;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class Client extends AbstractClient {
    private final String URL = "https://webpage.labstack.com/api/v1";
    private final JsonAdapter<ImageResponse> IMAGE_RESPONSE_JSON_ADAPTER = MOSHI.adapter(ImageResponse.class);
    private final JsonAdapter<PDFResponse> PDF_RESPONSE_JSON_ADAPTER = MOSHI.adapter(PDFResponse.class);

    public Client(String key) {
        super(key);
    }

    public ImageResponse image(ImageRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("image")
                .addQueryParameter("url", request.getUrl())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return IMAGE_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }

    public PDFResponse pdf(PDFRequest request) throws LabStackException {
        HttpUrl url = HttpUrl.parse(URL).newBuilder()
                .addPathSegment("pdf")
                .addQueryParameter("url", request.getUrl())
                .build();
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response res = okHttp.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw EXCEPTION_JSON_ADAPTER.fromJson(res.body().source());
            }
            return PDF_RESPONSE_JSON_ADAPTER.fromJson(res.body().source());
        } catch (IOException e) {
            throw LabStackException.builder().message(e.getMessage()).build();
        }
    }
}

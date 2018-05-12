package com.labstack;

import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.labstack.Client.API_URL;
import static com.labstack.Client.MOSHI;

@SuppressWarnings("Duplicates")
public class Webpage {
    private Client client;
    private JsonAdapter<PDFResponse> pdfResponseJsonAdapter = MOSHI.adapter(PDFResponse.class);

    public Webpage(Client client) {
        this.client = client;
    }

    public static class PDFOptions {
        private String layout;
        private String format;

        public String getLayout() {
            return layout;
        }

        public PDFOptions setLayout(String layout) {
            this.layout = layout;
            return this;
        }

        public String getFormat() {
            return format;
        }

        public PDFOptions setFormat(String format) {
            this.format = format;
            return this;
        }
    }

    public static class PDFResponse extends Download {
    }

    public PDFResponse webpagePDF(String url, PDFOptions options) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/webpage/pdf").newBuilder();
        httpBuider.addQueryParameter("url", url);
        httpBuider.addQueryParameter("layout", options.getLayout());
        httpBuider.addQueryParameter("format", options.getFormat());
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return pdfResponseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }
}

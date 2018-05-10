package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Client {
    private String apiKey;
    private OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .add(new Properties.JsonAdapter())
            .build();
    private static final String API_URL = "https://api.labstack.com";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    // JSON adapters
    private JsonAdapter<APIException> apiExceptionJsonAdapter = moshi.adapter(APIException.class);
    private JsonAdapter<Currency.ConvertRequest> currencyConvertRequestJsonAdapter = moshi.adapter(Currency.ConvertRequest.class);
    private JsonAdapter<Currency.ConvertResponse> currencyConvertResponseJsonAdapter = moshi.adapter(Currency.ConvertResponse.class);
    private JsonAdapter<Email.VerifyRequest> emailVerifyRequestJsonAdapter = moshi.adapter(Email.VerifyRequest.class);
    private JsonAdapter<Email.VerifyResponse> emailVerifyResponseJsonAdapter = moshi.adapter(Email.VerifyResponse.class);
    private JsonAdapter<Geocode.AddressRequest> geocodeAddressRequestJsonAdapter = moshi.adapter(Geocode.AddressRequest.class);
    private JsonAdapter<Geocode.IPRequest> geocodeIPRequestJsonAdapter = moshi.adapter(Geocode.IPRequest.class);
    private JsonAdapter<Geocode.ReverseRequest> geocodeReverseRequestJsonAdapter = moshi.adapter(Geocode.ReverseRequest.class);
    private JsonAdapter<Geocode.Response> geocodeResponseJsonAdapter = moshi.adapter(Geocode.Response.class);
    private JsonAdapter<Compress.ImageResponse> compressImageResponseJsonAdapter = moshi.adapter(Compress.ImageResponse.class);
    private JsonAdapter<Compress.PDFResponse> compressPDFResponseJsonAdapter = moshi.adapter(Compress.PDFResponse.class);
    private JsonAdapter<Watermark.ImageResponse> watermarkImageResponseJsonAdapter = moshi.adapter(Watermark.ImageResponse.class);
    private JsonAdapter<NLP.SentimentRequest> textSentimentRequestJsonAdapter = moshi.adapter(NLP.SentimentRequest.class);
    private JsonAdapter<NLP.SentimentResponse> textSentimentResponseJsonAdapter = moshi.adapter(NLP.SentimentResponse.class);
    private JsonAdapter<NLP.SpellcheckRequest> textSpellcheckRequestJsonAdapter = moshi.adapter(NLP.SpellcheckRequest.class);
    private JsonAdapter<NLP.SpellcheckResponse> textSpellcheckResponseJsonAdapter = moshi.adapter(NLP.SpellcheckResponse.class);
    private JsonAdapter<NLP.SummaryRequest> textSummaryRequestJsonAdapter = moshi.adapter(NLP.SummaryRequest.class);
    private JsonAdapter<NLP.SummaryResponse> textSummaryResponseJsonAdapter = moshi.adapter(NLP.SummaryResponse.class);
    private JsonAdapter<Webpage.PDFResponse> webpagePDFResponseJsonAdapter = moshi.adapter(Webpage.PDFResponse.class);

    public Client(String accountID, String apiKey) {
        this.apiKey = apiKey;
        okHttp = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.MINUTES)
                .readTimeout(20, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    public void download(String id, String path) {
        try (BufferedSink sink = Okio.buffer(Okio.sink(Paths.get(path)))) {
            Request request = new Request.Builder()
                    .url(Client.API_URL + "/download/" + id)
                    .build();
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download file: " + response);
            }
            sink.writeAll(response.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Currency.ConvertResponse currencyConvert(Currency.ConvertRequest request) {
        String json = currencyConvertRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/currency/convert")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return currencyConvertResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Email.VerifyResponse emailVerify(Email.VerifyRequest request) {
        String json = emailVerifyRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/email/verify")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return emailVerifyResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Geocode.Response geocodeAddress(Geocode.AddressRequest request) {
        String json = geocodeAddressRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/geocode/address")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return geocodeResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Geocode.Response geocodeIP(Geocode.IPRequest request) {
        String json = geocodeIPRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/geocode/ip")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return geocodeResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Geocode.Response geocodeReverse(Geocode.ReverseRequest request) {
        String json = geocodeReverseRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/geocode/reverse")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return geocodeResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Compress.ImageResponse compressImage(Compress.ImageRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/compress/image")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return compressImageResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Compress.PDFResponse compressPDF(Compress.PDFRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/compress/pdf")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return compressPDFResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Watermark.ImageResponse watermarkImage(Watermark.ImageRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .addFormDataPart("text", request.getText())
                    .addFormDataPart("font", request.getFont())
                    .addFormDataPart("size", String.valueOf(request.getSize()))
                    .addFormDataPart("color", request.getColor())
                    .addFormDataPart("opacity", String.valueOf(request.getOpacity()))
                    .addFormDataPart("position", request.getPosition())
                    .addFormDataPart("margin", String.valueOf(request.getMargin()))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/watermark/image")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return watermarkImageResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public NLP.SentimentResponse nlpSentiment(NLP.SentimentRequest request) {
        String json = textSentimentRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/text/sentiment")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return textSentimentResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public NLP.SpellcheckResponse nlpSpellcheck(NLP.SpellcheckRequest request) {
        String json = textSpellcheckRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/text/spellcheck")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return textSpellcheckResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public NLP.SummaryResponse nlpSummary(NLP.SummaryRequest request) {
        String json = textSummaryRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/text/summary")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return textSummaryResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Webpage.PDFResponse webpagePDF(Webpage.PDFRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/webpage/pdf").newBuilder();
        httpBuider.addQueryParameter("url", request.getUrl());
        httpBuider.addQueryParameter("layout", request.getLayout());
        httpBuider.addQueryParameter("format", request.getFormat());
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return webpagePDFResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }
}

class Interceptor implements okhttp3.Interceptor {
    private String apiKey;

    public Interceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request compressedRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return chain.proceed(compressedRequest);
    }
}

class Download {
    private String id;
    private String name;
    private long size;
    private String url;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }
}


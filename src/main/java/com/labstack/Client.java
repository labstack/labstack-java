package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Client {
    protected String apiKey;
    protected OkHttpClient okHttp;
    protected static final String API_URL = "https://api.labstack.com";
    protected static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    protected static final Moshi MOSHI = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
            .add(new Properties.JsonAdapter())
            .build();
    protected JsonAdapter<APIException> apiExceptionJsonAdapter = MOSHI.adapter(APIException.class);
//    private JsonAdapter<Compress.ImageResponse> compressImageResponseJsonAdapter = moshi.adapter(Compress.ImageResponse.class);
//    private JsonAdapter<Compress.PDFResponse> compressPDFResponseJsonAdapter = moshi.adapter(Compress.PDFResponse.class);
//    private JsonAdapter<NLP.SentimentRequest> textSentimentRequestJsonAdapter = moshi.adapter(NLP.SentimentRequest.class);
//    private JsonAdapter<NLP.SentimentResponse> textSentimentResponseJsonAdapter = moshi.adapter(NLP.SentimentResponse.class);
//    private JsonAdapter<NLP.SpellcheckRequest> textSpellcheckRequestJsonAdapter = moshi.adapter(NLP.SpellcheckRequest.class);
//    private JsonAdapter<NLP.SpellcheckResponse> textSpellcheckResponseJsonAdapter = moshi.adapter(NLP.SpellcheckResponse.class);
//    private JsonAdapter<NLP.SummaryRequest> textSummaryRequestJsonAdapter = moshi.adapter(NLP.SummaryRequest.class);
//    private JsonAdapter<NLP.SummaryResponse> textSummaryResponseJsonAdapter = moshi.adapter(NLP.SummaryResponse.class);

    public Client(String apiKey) {
        this.apiKey = apiKey;
        okHttp = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.MINUTES)
                .readTimeout(20, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    public Currency currency() {
        return new Currency(this);
    }

    public Geocode geocode() {
        return new Geocode(this);
    }

    public Email email() {
        return new Email(this);
    }

    public Watermark watermark() {
        return new Watermark(this);
    }

    public Webpage webpage() {
        return new Webpage(this);
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

//    public Compress.ImageResponse compressImage(Compress.ImageRequest request) {
//        try {
//            File file = new File(request.getFile());
//            RequestBody body = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
//                    .build();
//            Request req = new Request.Builder()
//                    .url(API_URL + "/compress/image")
//                    .post(body)
//                    .build();
//            Response res = okHttp.newCall(req).execute();
//            if (res.isSuccessful()) {
//                return compressImageResponseJsonAdapter.fromJson(res.body().source());
//            }
//            throw apiExceptionJsonAdapter.fromJson(res.body().source());
//        } catch (IOException e) {
//            throw new APIException(0, e.getMessage());
//        }
//    }

//    public Compress.PDFResponse compressPDF(Compress.PDFOptions request) {
//        try {
//            File file = new File(request.getFile());
//            RequestBody body = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
//                    .build();
//            Request req = new Request.Builder()
//                    .url(API_URL + "/compress/pdf")
//                    .post(body)
//                    .build();
//            Response res = okHttp.newCall(req).execute();
//            if (res.isSuccessful()) {
//                return compressPDFResponseJsonAdapter.fromJson(res.body().source());
//            }
//            throw apiExceptionJsonAdapter.fromJson(res.body().source());
//        } catch (IOException e) {
//            throw new APIException(0, e.getMessage());
//        }
//    }

//    public NLP.SentimentResponse nlpSentiment(NLP.SentimentRequest request) {
//        String json = textSentimentRequestJsonAdapter.toJson(request);
//        Request req = new Request.Builder()
//                .url(API_URL + "/text/sentiment")
//                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
//                .build();
//        try {
//            Response res = okHttp.newCall(req).execute();
//            if (res.isSuccessful()) {
//                return textSentimentResponseJsonAdapter.fromJson(res.body().source());
//            }
//            throw apiExceptionJsonAdapter.fromJson(res.body().source());
//        } catch (IOException e) {
//            throw new APIException(0, e.getMessage());
//        }
//    }

//    public NLP.SpellcheckResponse nlpSpellcheck(NLP.SpellcheckRequest request) {
//        String json = textSpellcheckRequestJsonAdapter.toJson(request);
//        Request req = new Request.Builder()
//                .url(API_URL + "/text/spellcheck")
//                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
//                .build();
//        try {
//            Response res = okHttp.newCall(req).execute();
//            if (res.isSuccessful()) {
//                return textSpellcheckResponseJsonAdapter.fromJson(res.body().source());
//            }
//            throw apiExceptionJsonAdapter.fromJson(res.body().source());
//        } catch (IOException e) {
//            throw new APIException(0, e.getMessage());
//        }
//    }

//    public NLP.SummaryResponse nlpSummary(NLP.SummaryRequest request) {
//        String json = textSummaryRequestJsonAdapter.toJson(request);
//        Request req = new Request.Builder()
//                .url(API_URL + "/text/summary")
//                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
//                .build();
//        try {
//            Response res = okHttp.newCall(req).execute();
//            if (res.isSuccessful()) {
//                return textSummaryResponseJsonAdapter.fromJson(res.body().source());
//            }
//            throw apiExceptionJsonAdapter.fromJson(res.body().source());
//        } catch (IOException e) {
//            throw new APIException(0, e.getMessage());
//        }
//    }
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


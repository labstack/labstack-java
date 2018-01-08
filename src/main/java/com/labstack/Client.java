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
    private Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe()).build();
    private static final String API_URL = "https://api.labstack.com";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    // JSON adapters
    private JsonAdapter<APIException> apiExceptionJsonAdapter = moshi.adapter(APIException.class);
    private JsonAdapter<Email.VerifyRequest> emailVerifyRequestJsonAdapter = moshi.adapter(Email.VerifyRequest.class);
    private JsonAdapter<Email.VerifyResponse> emailVerifyResponseJsonAdapter = moshi.adapter(Email.VerifyResponse.class);
    private JsonAdapter<Barcode.GenerateRequest> barcodeGenerateRequestJsonAdapter = moshi.adapter(Barcode.GenerateRequest.class);
    private JsonAdapter<Barcode.GenerateResponse> barcodeGenerateResponseJsonAdapter = moshi.adapter(Barcode.GenerateResponse.class);
    private JsonAdapter<Barcode.ScanResponse> barcodeScanResponseJsonAdapter = moshi.adapter(Barcode.ScanResponse.class);
    private JsonAdapter<Currency.ConvertRequest> currencyConvertRequestJsonAdapter = moshi.adapter(Currency.ConvertRequest.class);
    private JsonAdapter<Currency.ConvertResponse> currencyConvertResponseJsonAdapter = moshi.adapter(Currency.ConvertResponse.class);
    private JsonAdapter<DNS.LookupRequest> dnsLookupRequestJsonAdapter = moshi.adapter(DNS.LookupRequest.class);
    private JsonAdapter<DNS.LookupResponse> dnsLookupResponseJsonAdapter = moshi.adapter(DNS.LookupResponse.class);
    private JsonAdapter<Image.CompressResponse> imageCompressResponseJsonAdapter = moshi.adapter(Image.CompressResponse.class);
    private JsonAdapter<Image.ResizeResponse> imageResizeResponseJsonAdapter = moshi.adapter(Image.ResizeResponse.class);
    private JsonAdapter<Image.WatermarkResponse> imageWatermarkResponseJsonAdapter = moshi.adapter(Image.WatermarkResponse.class);
    private JsonAdapter<PDF.CompressResponse> pdfCompressResponseJsonAdapter = moshi.adapter(PDF.CompressResponse.class);
    private JsonAdapter<PDF.ImageResponse> pdfImageResponseJsonAdapter = moshi.adapter(PDF.ImageResponse.class);
    private JsonAdapter<PDF.SplitResponse> pdfSplitResponseJsonAdapter = moshi.adapter(PDF.SplitResponse.class);
    private JsonAdapter<Text.SentimentRequest> textSentimentRequestJsonAdapter = moshi.adapter(Text.SentimentRequest.class);
    private JsonAdapter<Text.SentimentResponse> textSentimentResponseJsonAdapter = moshi.adapter(Text.SentimentResponse.class);
    private JsonAdapter<Text.SpellcheckRequest> textSpellcheckRequestJsonAdapter = moshi.adapter(Text.SpellcheckRequest.class);
    private JsonAdapter<Text.SpellcheckResponse> textSpellcheckResponseJsonAdapter = moshi.adapter(Text.SpellcheckResponse.class);
    private JsonAdapter<Text.SummaryRequest> textSummaryRequestJsonAdapter = moshi.adapter(Text.SummaryRequest.class);
    private JsonAdapter<Text.SummaryResponse> textSummaryResponseJsonAdapter = moshi.adapter(Text.SummaryResponse.class);
    private JsonAdapter<Webpage.PDFRequest> webpagePDFRequestJsonAdapter = moshi.adapter(Webpage.PDFRequest.class);
    private JsonAdapter<Webpage.PDFResponse> webpagePDFResponseJsonAdapter = moshi.adapter(Webpage.PDFResponse.class);
    private JsonAdapter<Word.LookupRequest> wordLookupRequestJsonAdapter = moshi.adapter(Word.LookupRequest.class);
    private JsonAdapter<Word.LookupResponse> wordLookupResponseJsonAdapter = moshi.adapter(Word.LookupResponse.class);

    public Client(String apiKey) {
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

    public Barcode.GenerateResponse barcodeGenerate(Barcode.GenerateRequest request) {
        String json = barcodeGenerateRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/barcode/generate")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return barcodeGenerateResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Barcode.ScanResponse barcodeScan(Barcode.ScanRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/barcode/scan")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return barcodeScanResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
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

    public DNS.LookupResponse dnsLookup(DNS.LookupRequest request) {
        String json = dnsLookupRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/dns/lookup")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return dnsLookupResponseJsonAdapter.fromJson(res.body().source());
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

    public Image.CompressResponse imageCompress(Image.CompressRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/image/compress")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageCompressResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public PDF.CompressResponse pdfCompress(PDF.CompressRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/pdf/compress")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return pdfCompressResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public PDF.ImageResponse pdfImage(PDF.ImageRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/pdf/image")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return pdfImageResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public PDF.SplitResponse pdfSplit(PDF.SplitRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .addFormDataPart("pages", request.getPages())
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/pdf/split")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return pdfSplitResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Image.WatermarkResponse imageResize(Image.WatermarkRequest request) {
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
                    .url(API_URL + "/image/watermark")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageWatermarkResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Image.ResizeResponse imageResize(Image.ResizeRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .addFormDataPart("width", String.valueOf(request.getWidth()))
                    .addFormDataPart("height", String.valueOf(request.getHeight()))
                    .addFormDataPart("format", String.valueOf(request.getFormat()))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/image/resize")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageResizeResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

    public Text.SentimentResponse textSentiment(Text.SentimentRequest request) {
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

    public Text.SpellcheckResponse textSpellcheck(Text.SpellcheckRequest request) {
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

    public Text.SummaryResponse textSummary(Text.SummaryRequest request) {
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
        String json = webpagePDFRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/webpage/pdf")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
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

    public Word.LookupResponse wordLookup(Word.LookupRequest request) {
        String json = wordLookupRequestJsonAdapter.toJson(request);
        Request req = new Request.Builder()
                .url(API_URL + "/word/lookup")
                .post(RequestBody.create(MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return wordLookupResponseJsonAdapter.fromJson(res.body().source());
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



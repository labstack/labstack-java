package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@SuppressWarnings("Duplicates")
public class Client {
    private String apiKey;
    private OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe()).build();
    private static final String API_URL = "https://api.labstack.com";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    // JSON adapters
    private JsonAdapter<Image.CompressResponse> imageCompressResponseJsonAdapter = moshi.adapter(Image.CompressResponse.class);
    private JsonAdapter<Image.ResizeResponse> imageResizeResponseJsonAdapter = moshi.adapter(Image.ResizeResponse.class);
    private JsonAdapter<ApiException> apiExceptionJsonAdapter = moshi.adapter(ApiException.class);

    public Client(String apiKey) {
        this.apiKey = apiKey;
        okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor(apiKey))
                .build();
    }

    public Image.CompressResponse ImageCompress(Image.CompressRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .build();
            Request req = new Request.Builder()
                    .url(Client.API_URL + "/image/compress")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageCompressResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new ApiException(0, e.getMessage());
        }
    }

    public Image.ResizeResponse ImageResize(Image.ResizeRequest request) {
        try {
            File file = new File(request.getFile());
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(null, file))
                    .addFormDataPart("width", String.valueOf(request.getWidth()))
                    .addFormDataPart("height", String.valueOf(request.getHeight()))
                    .addFormDataPart("crop", String.valueOf(request.isCrop()))
                    .build();
            Request req = new Request.Builder()
                    .url(Client.API_URL + "/image/resize")
                    .post(body)
                    .build();
            Response res = okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageResizeResponseJsonAdapter.fromJson(res.body().source());
            }
            throw apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new ApiException(0, e.getMessage());
        }
    }
}

class Interceptor implements okhttp3.Interceptor {
    private String apiKey;

    public Interceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(okhttp3.Interceptor.Chain chain) throws IOException {
        Request compressedRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + apiKey)
                .build();
        return chain.proceed(compressedRequest);
    }
}

class Download {
    private String id;
    private String name;
    private String url;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}



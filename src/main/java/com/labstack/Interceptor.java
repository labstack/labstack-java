package com.labstack;

import java.io.IOException;
import com.labstack.currency.Convert;
import com.labstack.currency.ConvertRequest;
import com.labstack.currency.List;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Interceptor implements okhttp3.Interceptor {
    private String key;

    Interceptor(String key) {
        this.key = key;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request compressedRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + key)
                .build();
        return chain.proceed(compressedRequest);
    }

    public static class Currency {
        private static final String URL = "https://currency.labstack.com/api/v1";
        private OkHttpClient okHttp;

        public Currency(String key) {
            okHttp = Client.okHttpClient(key);
        }

        public Convert.Response convert(ConvertRequest request) {
            HttpUrl.Builder builder = HttpUrl.parse(URL + "/convert").newBuilder();
            builder.addPathSegment(request.getAmount().toString());
            builder.addPathSegment(request.getFrom());
            builder.addPathSegment(request.getTo());
            Request req = new Request.Builder().url(builder.build()).build();
            try {
                Response res = okHttp.newCall(req).execute();
                if (res.isSuccessful()) {
                    return Convert.responseJsonAdapter.fromJson(res.body().source());
                }
                throw APIException.JsonAdapter.fromJson(res.body().source());
            } catch (IOException e) {
                throw new APIException(0, e.getMessage());
            }
        }

        public List.Response list(List.Request request) {
            HttpUrl.Builder builder = HttpUrl.parse(URL + "/list").newBuilder();
            Request req = new Request.Builder().url(builder.build()).build();
            try {
                Response res = okHttp.newCall(req).execute();
                if (res.isSuccessful()) {
                    return List.responseJsonAdapter.fromJson(res.body().source());
                }
                throw APIException.JsonAdapter.fromJson(res.body().source());
            } catch (IOException e) {
                throw new APIException(0, e.getMessage());
            }
        }
    }
}

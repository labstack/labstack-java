package com.labstack;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.labstack.Client.API_URL;
import static com.labstack.Client.MOSHI;

@SuppressWarnings("Duplicates")
public class Post {
    private Client client;
    private JsonAdapter<VerifyResponse> verifyResponseJsonAdapter = MOSHI.adapter(VerifyResponse.class);

    public Post(Client client) {
        this.client = client;
    }

    public static class VerifyRequest {
        private String email;

        public String getEmail() {
            return email;
        }

        public VerifyRequest setEmail(String email) {
            this.email = email;
            return this;
        }
    }

    public static class VerifyResponse {
        @Json(name = "valid_syntax")
        private Boolean validSyntax;
        private Boolean deliverable;
        @Json(name = "inbox_full")
        private Boolean inboxFull;
        @Json(name = "valid_domain")
        private Boolean validDomain;
        private Boolean disposable;
        @Json(name = "catch_all")
        private Boolean catchAll;

        public Boolean getValidSyntax() {
            return validSyntax;
        }

        public Boolean getDeliverable() {
            return deliverable;
        }

        public Boolean getInboxFull() {
            return inboxFull;
        }

        public Boolean getValidDomain() {
            return validDomain;
        }

        public Boolean getDisposable() {
            return disposable;
        }

        public Boolean getCatchAll() {
            return catchAll;
        }
    }

    public VerifyResponse verify(VerifyRequest request) {
        HttpUrl.Builder httpBuider = HttpUrl.parse(API_URL + "/post/verify").newBuilder();
        httpBuider.addQueryParameter("email", request.getEmail());
        Request req = new Request.Builder().url(httpBuider.build()).build();
        try {
            Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return verifyResponseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }

}

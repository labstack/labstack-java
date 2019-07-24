package com.labstack.email;

import static com.labstack.Client.MOSHI;
import com.squareup.moshi.JsonAdapter;


public class Verify {
    static final JsonAdapter<Response> responseJsonAdapter = MOSHI.adapter(Verify.Response.class);

    static class Request {
        private String email;

        String getEmail() {
            return email;
        }

        Request setEmail(final String email) {
            this.email = email;
            return this;
        }
    }

    static class Response {
    }
}

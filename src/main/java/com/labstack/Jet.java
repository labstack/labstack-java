package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;

public class Jet {
    private OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe()).build();
    private JsonAdapter<JetMessage> messageJsonAdapter = moshi.adapter(JetMessage.class);
    private JsonAdapter<JetException> exceptionJsonAdapter = moshi.adapter(JetException.class);

    protected Jet(OkHttpClient okHttp) {
        this.okHttp = okHttp;
    }

    public JetMessage send(JetMessage message) throws JetException {
        try {
            message.addInlines();
            message.addAttachments();
            String json = messageJsonAdapter.toJson(message);
            Request request = new Request.Builder()
                    .url(Client.API_URL + "/jet/send")
                    .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                    .build();
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                return messageJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new JetException(0, e.getMessage());
        }
    }
}


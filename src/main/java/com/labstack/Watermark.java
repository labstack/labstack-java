package com.labstack;

import com.squareup.moshi.JsonAdapter;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

import static com.labstack.Client.API_URL;
import static com.labstack.Client.MOSHI;

public class Watermark {
    private Client client;
    private JsonAdapter<ImageResponse> imageResponseJsonAdapter = MOSHI.adapter(ImageResponse.class);

    public Watermark(Client client) {
        this.client = client;
    }

    public static class ImageOptions {
        private String font;
        private int size;
        private String color;
        private int opacity;
        private String position;
        private int margin;

        public ImageOptions setFont(String font) {
            this.font = font;
            return this;
        }

        public ImageOptions setSize(int size) {
            this.size = size;
            return this;
        }

        public ImageOptions setColor(String color) {
            this.color = color;
            return this;
        }

        public ImageOptions setOpacity(int opacity) {
            this.opacity = opacity;
            return this;
        }

        public ImageOptions setPosition(String position) {
            this.position = position;
            return this;
        }

        public ImageOptions setMargin(int margin) {
            this.margin = margin;
            return this;
        }
    }

    public static class ImageResponse extends Download {
    }

    public ImageResponse image(String file, String text) {
        return image(file, text, new ImageOptions());
    }

    public ImageResponse image(String file, String text, ImageOptions options) {
        try {
            File f = new File(file);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", f.getName(), RequestBody.create(null, file))
                    .addFormDataPart("text", text)
                    .addFormDataPart("font", options.font)
                    .addFormDataPart("size", String.valueOf(options.size))
                    .addFormDataPart("color", options.color)
                    .addFormDataPart("opacity", String.valueOf(options.opacity))
                    .addFormDataPart("position", options.position)
                    .addFormDataPart("margin", String.valueOf(options.margin))
                    .build();
            Request req = new Request.Builder()
                    .url(API_URL + "/watermark/image")
                    .post(body)
                    .build();
            Response res = client.okHttp.newCall(req).execute();
            if (res.isSuccessful()) {
                return imageResponseJsonAdapter.fromJson(res.body().source());
            }
            throw client.apiExceptionJsonAdapter.fromJson(res.body().source());
        } catch (IOException e) {
            throw new APIException(0, e.getMessage());
        }
    }
}

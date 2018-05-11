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

    public static class ImageRequest {
        private String file;
        private String text;
        private String font;
        private int size;
        private String color;
        private int opacity;
        private String position;
        private int margin;

        public String getFile() {
            return file;
        }

        public ImageRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public String getText() {
            return text;
        }

        public ImageRequest setText(String text) {
            this.text = text;
            return this;
        }

        public String getFont() {
            return font;
        }

        public ImageRequest setFont(String font) {
            this.font = font;
            return this;
        }

        public int getSize() {
            return size;
        }

        public ImageRequest setSize(int size) {
            this.size = size;
            return this;
        }

        public String getColor() {
            return color;
        }

        public ImageRequest setColor(String color) {
            this.color = color;
            return this;
        }

        public int getOpacity() {
            return opacity;
        }

        public ImageRequest setOpacity(int opacity) {
            this.opacity = opacity;
            return this;
        }

        public String getPosition() {
            return position;
        }

        public ImageRequest setPosition(String position) {
            this.position = position;
            return this;
        }

        public int getMargin() {
            return margin;
        }

        public ImageRequest setMargin(int margin) {
            this.margin = margin;
            return this;
        }
    }

    public static class ImageResponse extends Download {
    }

    public ImageResponse image(ImageRequest request) {
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

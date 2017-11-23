package com.labstack;

import com.squareup.moshi.Json;

public class Barcode {
    public static class GenerateRequest {
        private String format;
        private String content;
        private int width;
        private int heigth;

        public GenerateRequest setFormat(String format) {
            this.format = format;
            return this;
        }

        public GenerateRequest setContent(String content) {
            this.content = content;
            return this;
        }

        public GenerateRequest setWidth(int width) {
            this.width = width;
            return this;
        }

        public GenerateRequest setHeigth(int heigth) {
            this.heigth = heigth;
            return this;
        }
    }

    public static class GenerateResponse extends Download {
    }

    public static class ScanRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public ScanRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class ScanResponse {
        private String format;
        private String content;
        @Json(name = "content_type")
        private String contentType;

        public String getFormat() {
            return format;
        }

        public String getContent() {
            return content;
        }

        public String getContentType() {
            return contentType;
        }
    }
}

package com.labstack;

public class Webpage {
    public static class PDFRequest {
        private String url;
        private String layout;
        private String format;

        public String getUrl() {
            return url;
        }

        public PDFRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getLayout() {
            return layout;
        }

        public PDFRequest setLayout(String layout) {
            this.layout = layout;
            return this;
        }

        public String getFormat() {
            return format;
        }

        public PDFRequest setFormat(String format) {
            this.format = format;
            return this;
        }
    }

    public static class PDFResponse extends Download {
    }
}

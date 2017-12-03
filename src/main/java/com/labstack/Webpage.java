package com.labstack;

public class Webpage {
    public static class PDFRequest {
        private String url;
        private String size;
        private String layout;

        public PDFRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public PDFRequest setSize(String size) {
            this.size = size;
            return this;
        }

        public PDFRequest setLayout(String layout) {
            this.layout = layout;
            return this;
        }
    }

    public static class PDFResponse extends Download {
    }
}

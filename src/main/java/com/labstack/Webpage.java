package com.labstack;

public class Webpage {
    public static class PdfRequest {
        private String url;
        private String size;
        private String layout;

        public PdfRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public PdfRequest setSize(String size) {
            this.size = size;
            return this;
        }

        public PdfRequest setLayout(String layout) {
            this.layout = layout;
            return this;
        }
    }

    public static class PdfResponse extends Download {
    }
}

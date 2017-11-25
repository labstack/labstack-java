package com.labstack;

public class Webpage {
    public static class ToPdfRequest {
        private String url;
        private String size;
        private String layout;

        public ToPdfRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public ToPdfRequest setSize(String size) {
            this.size = size;
            return this;
        }

        public ToPdfRequest setLayout(String layout) {
            this.layout = layout;
            return this;
        }
    }

    public static class ToPdfResponse extends Download {
    }
}

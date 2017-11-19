package com.labstack;

public class Barcode {
    public static class GenerateRequest {
        private String format;
        private String content;
        private String size;

        public String getFormat() {
            return format;
        }

        public GenerateRequest setFormat(String format) {
            this.format = format;
            return this;
        }

        public String getContent() {
            return content;
        }

        public GenerateRequest setContent(String content) {
            this.content = content;
            return this;
        }

        public String getSize() {
            return size;
        }

        public GenerateRequest setSize(String size) {
            this.size = size;
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

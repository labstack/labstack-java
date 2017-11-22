package com.labstack;

public class Pdf {
    public static class ToImageRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public ToImageRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class ToImageResponse extends Download {
    }

    public static class ExtractImageRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public ExtractImageRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class ExtractImageResponse extends Download {
    }
}

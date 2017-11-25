package com.labstack;

public class Pdf {
    public static class ImageRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public ImageRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class ImageResponse extends Download {
    }
}

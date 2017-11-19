package com.labstack;

public class Image {
    public static class CompressRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public CompressRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class CompressResponse extends Download {
        private long size;

        public long getSize() {
            return size;
        }
    }

    public static class ResizeRequest {
        private String file;
        private int width;
        private int height;
        private boolean crop;

        public String getFile() {
            return file;
        }

        public ResizeRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public int getWidth() {
            return width;
        }

        public ResizeRequest setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public ResizeRequest setHeight(int height) {
            this.height = height;
            return this;
        }

        public boolean isCrop() {
            return crop;
        }

        public ResizeRequest setCrop(boolean crop) {
            this.crop = crop;
            return this;
        }
    }

    public static class ResizeResponse extends Download {
        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}

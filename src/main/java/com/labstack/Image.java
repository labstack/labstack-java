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
    }

    public static class ResizeRequest {
        private String file;
        private int width;
        private int height;
        private String format;

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

        public String getFormat() {
            return format;
        }

        public ResizeRequest setFormat(String format) {
            this.format = format;
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

    public static class WatermarkRequest {
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

        public WatermarkRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public String getText() {
            return text;
        }

        public WatermarkRequest setText(String text) {
            this.text = text;
            return this;
        }

        public String getFont() {
            return font;
        }

        public WatermarkRequest setFont(String font) {
            this.font = font;
            return this;
        }

        public int getSize() {
            return size;
        }

        public WatermarkRequest setSize(int size) {
            this.size = size;
            return this;
        }

        public String getColor() {
            return color;
        }

        public WatermarkRequest setColor(String color) {
            this.color = color;
            return this;
        }

        public int getOpacity() {
            return opacity;
        }

        public WatermarkRequest setOpacity(int opacity) {
            this.opacity = opacity;
            return this;
        }

        public String getPosition() {
            return position;
        }

        public WatermarkRequest setPosition(String position) {
            this.position = position;
            return this;
        }

        public int getMargin() {
            return margin;
        }

        public WatermarkRequest setMargin(int margin) {
            this.margin = margin;
            return this;
        }
    }

    public static class WatermarkResponse extends Download {
    }
}

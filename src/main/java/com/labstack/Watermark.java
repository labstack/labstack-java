package com.labstack;

public class Watermark {
    public static class ImageRequest {
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

        public ImageRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public String getText() {
            return text;
        }

        public ImageRequest setText(String text) {
            this.text = text;
            return this;
        }

        public String getFont() {
            return font;
        }

        public ImageRequest setFont(String font) {
            this.font = font;
            return this;
        }

        public int getSize() {
            return size;
        }

        public ImageRequest setSize(int size) {
            this.size = size;
            return this;
        }

        public String getColor() {
            return color;
        }

        public ImageRequest setColor(String color) {
            this.color = color;
            return this;
        }

        public int getOpacity() {
            return opacity;
        }

        public ImageRequest setOpacity(int opacity) {
            this.opacity = opacity;
            return this;
        }

        public String getPosition() {
            return position;
        }

        public ImageRequest setPosition(String position) {
            this.position = position;
            return this;
        }

        public int getMargin() {
            return margin;
        }

        public ImageRequest setMargin(int margin) {
            this.margin = margin;
            return this;
        }
    }

    public static class ImageResponse extends Download {
    }
}

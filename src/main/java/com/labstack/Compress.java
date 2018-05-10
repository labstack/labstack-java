package com.labstack;

public class Compress {
    public static class AudioRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public AudioRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class AudioResponse extends Download {
    }

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

    public static class PDFRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public PDFRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class PDFResponse extends Download {
    }

    public static class VideoRequest {
        private String file;

        public String getFile() {
            return file;
        }

        public VideoRequest setFile(String file) {
            this.file = file;
            return this;
        }
    }

    public static class VideoResponse extends Download {
    }
}

package com.labstack;

public class PDF {
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

    public static class ImageRequest {
        private String file;
        private boolean extract;

        public String getFile() {
            return file;
        }

        public ImageRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public boolean isExtract() {
            return extract;
        }

        public ImageRequest setExtract(boolean extract) {
            this.extract = extract;
            return this;
        }
    }

    public static class ImageResponse extends Download {
    }

    public static class SplitRequest {
        private String file;
        private String pages;

        public String getFile() {
            return file;
        }

        public SplitRequest setFile(String file) {
            this.file = file;
            return this;
        }

        public String getPages() {
            return pages;
        }

        public SplitRequest setPages(String pages) {
            this.pages = pages;
            return this;
        }
    }

    public static class SplitResponse extends Download {
    }
}

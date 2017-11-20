package com.labstack;

import java.util.List;

public class Text {
    public static class SentimentRequest {
        private String text;

        public SentimentRequest setText(String text) {
            this.text = text;
            return this;
        }
    }

    public static class SentimentResponse {
        private float subjectivity;
        private float polarity;

        public float getSubjectivity() {
            return subjectivity;
        }

        public float getPolarity() {
            return polarity;
        }
    }

    public static class SpellCheckRequest {
        private String text;

        public SpellCheckRequest setText(String text) {
            this.text = text;
            return this;
        }
    }

    public static class SpellCheckResponse {
        private List<SpellCheckMisspelled> misspelled;

        public List<SpellCheckMisspelled> getMisspelled() {
            return misspelled;
        }
    }

    public static class SpellCheckMisspelled {
        private String word;
        private int offset;
        private List<String> suggestions;

        public String getWord() {
            return word;
        }

        public int getOffset() {
            return offset;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }
    }

    public static class SummaryRequest {
        private String text;
        private String url;
        private String language;
        private int length;

        public SummaryRequest setText(String text) {
            this.text = text;
            return this;
        }

        public SummaryRequest setUrl(String url) {
            this.url = url;
            return this;
        }

        public SummaryRequest setLanguage(String language) {
            this.language = language;
            return this;
        }

        public SummaryRequest setLength(int length) {
            this.length = length;
            return this;
        }
    }

    public static class SummaryResponse {
        private String summary;

        public String getSummary() {
            return summary;
        }
    }
}

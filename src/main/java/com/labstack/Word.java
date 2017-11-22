package com.labstack;

import java.util.List;

public class Word {
    public static class LookupRequest {
        private String word;

        public LookupRequest setWord(String word) {
            this.word = word;
            return this;
        }
    }

    public static class LookupResponse {
        private List<String> pronunciation;
        private List<String> rhymes;
        private List<LookupResult> noun;
        private List<LookupResult> verb;
        private List<LookupResult> adverb;
        private List<LookupResult> adjective;

    }

    public static class LookupResult {
        private String definition;
        private List<String> synonyms;
        private List<String> antonyms;
        private List<String> examples;

        public String getDefinition() {
            return definition;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }

        public List<String> getAntonyms() {
            return antonyms;
        }

        public List<String> getExamples() {
            return examples;
        }
    }
}

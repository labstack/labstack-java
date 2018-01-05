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

    public static class LookupResponse extends Response {
        private List<String> pronunciation;
        private List<String> rhymes;
        private List<LookupResult> nouns;
        private List<LookupResult> verbs;
        private List<LookupResult> adverbs;
        private List<LookupResult> adjectives;

        public List<String> getPronunciation() {
            return pronunciation;
        }

        public List<String> getRhymes() {
            return rhymes;
        }

        public List<LookupResult> getNouns() {
            return nouns;
        }

        public List<LookupResult> getVerbs() {
            return verbs;
        }

        public List<LookupResult> getAdverbs() {
            return adverbs;
        }

        public List<LookupResult> getAdjectives() {
            return adjectives;
        }
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

package com.labstack.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class ClientTest {
    private final Client client = new Client(System.getenv("KEY"));

    @Test
    void dns() {
        assertDoesNotThrow(() -> {
            SearchRequest request = SearchRequest.builder().domain("twilio.com").build();
            SearchResponse response = client.search(request);
            assertNotEquals(0, response.getResults().length);
        });
    }

    @Test
    void search() {
        assertDoesNotThrow(() -> {
            SearchRequest request = SearchRequest.builder().domain("twilio.com").build();
            SearchResponse response = client.search(request);
            assertNotEquals(0, response.getResults().length);
        });
    }

    @Test
    void status() {
        assertDoesNotThrow(() -> {
            StatusRequest request = StatusRequest.builder().domain("twilio.com").build();
            StatusResponse response = client.status(request);
            assertNotEquals("", response.getResult());
        });
    }

    @Test
    void whois() {
        assertDoesNotThrow(() -> {
            WhoisRequest request = WhoisRequest.builder().domain("twilio.com").build();
            WhoisResponse response = client.whois(request);
            assertNotEquals("", response.getRaw());
        });
    }
}
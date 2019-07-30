package com.labstack.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class DomainClientTest {
    private final DomainClient client = new DomainClient(System.getenv("KEY"));

    @Test
    void dns() {
        assertDoesNotThrow(() -> {
            DomainDNSRequest request = DomainDNSRequest.builder().type("A").domain("twilio.com").build();
            DomainDNSResponse response = client.dns(request);
            assertNotEquals(0, response.getRecords().length);
        });
    }

    @Test
    void search() {
        assertDoesNotThrow(() -> {
            DomainSearchRequest request = DomainSearchRequest.builder().domain("twilio.com").build();
            DomainSearchResponse response = client.search(request);
            assertNotEquals(0, response.getResults().length);
        });
    }

    @Test
    void status() {
        assertDoesNotThrow(() -> {
            DomainStatusRequest request = DomainStatusRequest.builder().domain("twilio.com").build();
            DomainStatusResponse response = client.status(request);
            assertNotEquals("", response.getResult());
        });
    }

    @Test
    void whois() {
        assertDoesNotThrow(() -> {
            DomainWhoisRequest request = DomainWhoisRequest.builder().domain("twilio.com").build();
            DomainWhoisResponse response = client.whois(request);
            assertNotEquals("", response.getRaw());
        });
    }
}
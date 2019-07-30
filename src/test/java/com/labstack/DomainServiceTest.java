package com.labstack;

import static com.labstack.ClientTest.DOMAIN_SERVICE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import com.labstack.domain.SearchRequest;
import com.labstack.domain.SearchResponse;
import com.labstack.domain.StatusRequest;
import com.labstack.domain.StatusResponse;
import com.labstack.domain.WhoisRequest;
import com.labstack.domain.WhoisResponse;


class DomainServiceTest {

    @Test
    void dns() {
        assertDoesNotThrow(() -> {
            SearchRequest request = SearchRequest.builder().domain("twilio.com").build();
            SearchResponse response = DOMAIN_SERVICE.search(request);
            assertNotEquals(0, response.getResults().length);
        });
    }

    @Test
    void search() {
        assertDoesNotThrow(() -> {
            SearchRequest request = SearchRequest.builder().domain("twilio.com").build();
            SearchResponse response = DOMAIN_SERVICE.search(request);
            assertNotEquals(0, response.getResults().length);
        });
    }

    @Test
    void status() {
        assertDoesNotThrow(() -> {
            StatusRequest request = StatusRequest.builder().domain("twilio.com").build();
            StatusResponse response = DOMAIN_SERVICE.status(request);
            assertNotEquals("", response.getResult());
        });
    }

    @Test
    void whois() {
        assertDoesNotThrow(() -> {
            WhoisRequest request = WhoisRequest.builder().domain("twilio.com").build();
            WhoisResponse response = DOMAIN_SERVICE.whois(request);
            assertNotEquals("", response.getRaw());
        });
    }
}
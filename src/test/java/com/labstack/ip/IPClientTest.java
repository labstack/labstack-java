package com.labstack.ip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class IPClientTest {
    private final IPClient client = new IPClient(System.getenv("KEY"));

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            IPLookupRequest request = IPLookupRequest.builder().ip("96.45.83.67").build();
            IPLookupResponse response = client.lookup(request);
            assertNotEquals("", response.getCountry());
        });
    }
}
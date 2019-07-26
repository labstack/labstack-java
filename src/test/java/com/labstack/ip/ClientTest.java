package com.labstack.ip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class ClientTest {
    private final Client client = new Client(System.getenv("KEY"));

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            LookupRequest request = LookupRequest.builder().ip("96.45.83.67").build();
            LookupResponse response = client.lookup(request);
            assertNotEquals("", response.getCountry());
        });
    }
}
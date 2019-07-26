package com.labstack.email;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class ClientTest {
    private final Client client = new Client(System.getenv("KEY"));

    @Test
    void verify() {
        assertDoesNotThrow(() -> {
            VerifyRequest request = VerifyRequest.builder().build().builder().email("jon@labstack.com").build();
            VerifyResponse response = client.verify(request);
            assertEquals("deliverable", response.getResult());
        });
    }
}
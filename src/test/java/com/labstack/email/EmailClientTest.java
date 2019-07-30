package com.labstack.email;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class EmailClientTest {
    private final EmailClient client = new EmailClient(System.getenv("KEY"));

    @Test
    void verify() {
        assertDoesNotThrow(() -> {
            EmailVerifyRequest request = EmailVerifyRequest.builder().email("jon@labstack.com").build();
            EmailVerifyResponse response = client.verify(request);
            assertEquals("deliverable", response.getResult());
        });
    }
}
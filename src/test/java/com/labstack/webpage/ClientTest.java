package com.labstack.webpage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class ClientTest {
    private final Client client = new Client(System.getenv("KEY"));

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            ImageRequest request = ImageRequest.builder().url("http://amazon.com").build();
            ImageResponse response = client.image(request);
            assertNotEquals("", response.getImage());
        });
    }

    @Test
    void pdf() {
        assertDoesNotThrow(() -> {
            PDFRequest request = PDFRequest.builder().url("http://amazon.com").build();
            PDFResponse response = client.pdf(request);
            assertNotEquals("", response.getPdf());
        });
    }
}
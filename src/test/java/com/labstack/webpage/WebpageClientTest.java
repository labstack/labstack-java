package com.labstack.webpage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class WebpageClientTest {
    private final WebpageClient client = new WebpageClient(System.getenv("KEY"));

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            WebpageImageRequest request = WebpageImageRequest.builder().url("http://amazon.com").build();
            WebpageImageResponse response = client.image(request);
            assertNotEquals("", response.getImage());
        });
    }

    @Test
    void pdf() {
        assertDoesNotThrow(() -> {
            WebpagePDFRequest request = WebpagePDFRequest.builder().url("http://amazon.com").build();
            WebpagePDFResponse response = client.pdf(request);
            assertNotEquals("", response.getPdf());
        });
    }
}
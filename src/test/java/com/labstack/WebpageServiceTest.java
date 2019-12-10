package com.labstack;

import static com.labstack.ClientTest.WEBPAGE_SERVICE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import com.labstack.webpage.ImageRequest;
import com.labstack.webpage.ImageResponse;
import com.labstack.webpage.PDFRequest;
import com.labstack.webpage.PDFResponse;


class WebpageServiceTest {

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            ImageRequest request = ImageRequest.builder().url("http://amazon.com").build();
            ImageResponse response = WEBPAGE_SERVICE.image(request);
            assertNotEquals("", response.getImage());
        });
    }

    @Test
    void pdf() {
        assertDoesNotThrow(() -> {
            PDFRequest request = PDFRequest.builder().url("http://amazon.com").build();
            PDFResponse response = WEBPAGE_SERVICE.pdf(request);
            assertNotEquals("", response.getPdf());
        });
    }
}
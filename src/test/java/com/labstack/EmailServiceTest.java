package com.labstack;

import static com.labstack.ClientTest.EMAIL_SERVICE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.labstack.email.VerifyRequest;
import com.labstack.email.VerifyResponse;


class EmailServiceTest {

    @Test
    void verify() {
        assertDoesNotThrow(() -> {
            VerifyRequest request = VerifyRequest.builder().email("jon@labstack.com").build();
            VerifyResponse response = EMAIL_SERVICE.verify(request);
            assertEquals("deliverable", response.getResult());
        });
    }
}
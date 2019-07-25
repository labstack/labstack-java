package com.labstack.currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;


class ClientTest {
    private final Client client = new Client(System.getenv("KEY"));

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            ConvertRequest request = ConvertRequest.builder().amount(10D).from("USD").to("INR").build();
            ConvertResponse response = client.convert(request);
            // assertNotEquals(0, response.getAmount());
        });
    }
}
package com.labstack.currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;


class CurrencyClientTest {
    private final CurrencyClient client = new CurrencyClient(System.getenv("KEY"));

    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            CurrencyConvertRequest request = CurrencyConvertRequest.builder().amount(10D).from("USD").to("INR").build();
            CurrencyConvertResponse response = client.convert(request);
            assertNotEquals(0, response.getAmount());
        });
    }

    @Test
    void list() {
        assertDoesNotThrow(() -> {
            CurrencyListResponse response = client.list(new CurrencyListRequest());
            assertNotEquals(0, response.getCurrencies().length);
        });
    }
}
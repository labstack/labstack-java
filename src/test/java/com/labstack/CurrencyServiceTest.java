package com.labstack;

import static com.labstack.ClientTest.CURRENCY_SERVICE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import com.labstack.currency.ConvertRequest;
import com.labstack.currency.ConvertResponse;
import com.labstack.currency.ListRequest;
import com.labstack.currency.ListResponse;


class CurrencyServiceTest {
    @Test
    void convert() {
        assertDoesNotThrow(() -> {
            ConvertRequest request = ConvertRequest.builder().amount(10D).from("USD").to("INR").build();
            ConvertResponse response = CURRENCY_SERVICE.convert(request);
            assertNotEquals(0, response.getAmount());
        });
    }

    @Test
    void list() {
        assertDoesNotThrow(() -> {
            ListResponse response = CURRENCY_SERVICE.list(null);
            assertNotEquals(0, response.getCurrencies().length);
        });
    }
}
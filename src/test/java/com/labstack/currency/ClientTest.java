package com.labstack.currency;

import static org.junit.Assert.assertNull;
import org.junit.Test;
import com.labstack.LabstackException;


public class ClientTest {
    private static final Client client = new Client(System.getenv("KEY"));

    @Test
    public void convert() {
        try {
            client.convert(10, "USD", "IND");
        } catch (LabstackException e) {
            assertNull(e);
        }
    }
}
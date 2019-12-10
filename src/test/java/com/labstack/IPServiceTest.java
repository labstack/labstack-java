package com.labstack;

import static com.labstack.ClientTest.IP_SERVICE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import com.labstack.ip.LookupRequest;
import com.labstack.ip.LookupResponse;


class IPServiceTest {

    @Test
    void lookup() {
        assertDoesNotThrow(() -> {
            LookupRequest request = LookupRequest.builder().ip("96.45.83.67").build();
            LookupResponse response = IP_SERVICE.lookup(request);
            assertNotEquals("", response.getCountry());
        });
    }
}
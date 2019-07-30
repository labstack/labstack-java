package com.labstack;

import org.junit.jupiter.api.Test;


class ClientTest {
    private static final Client CLIENT = new Client(System.getenv("KEY"));
    static final CurrencyService CURRENCY_SERVICE = CLIENT.currency();
    static final DomainService DOMAIN_SERVICE = CLIENT.domain();
    static final EmailService EMAIL_SERVICE = CLIENT.email();
    static final IPService IP_SERVICE = CLIENT.ip();
    static final WebpageService WEBPAGE_SERVICE = CLIENT.webpage();

    @Test
    void currency() {
    }
}
package com.labstack.currency;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class CurrencyConvertRequest {
    private Double amount;
    private String from;
    private String to;
}

package com.labstack.currency;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class CurrencyListResponse {
    private Currency[] currencies;
}

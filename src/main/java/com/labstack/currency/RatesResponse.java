package com.labstack.currency;

import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RatesResponse {
    private Date time;
    private String base;
    private Map<String, Double> rates;
}

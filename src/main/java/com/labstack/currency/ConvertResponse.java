package com.labstack.currency;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ConvertResponse {
    private Date time;
    private Double amount;
}

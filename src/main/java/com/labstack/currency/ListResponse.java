package com.labstack.currency;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class ListResponse {
    private List<Currency> currencies;
}

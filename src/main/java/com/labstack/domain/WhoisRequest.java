package com.labstack.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class WhoisRequest {
    private String domain;
}

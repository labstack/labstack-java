package com.labstack.domain;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DomainStatusResponse {
    private String domain;
    private String zone;
    private String result;
    private String[] Flags;
}

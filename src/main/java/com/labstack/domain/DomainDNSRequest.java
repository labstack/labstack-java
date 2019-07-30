package com.labstack.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class DomainDNSRequest {
    private String type;
    private String domain;
}

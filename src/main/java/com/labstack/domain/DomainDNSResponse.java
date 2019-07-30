package com.labstack.domain;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DomainDNSResponse {
    private Record[] records;
}

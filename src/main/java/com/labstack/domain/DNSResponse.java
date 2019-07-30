package com.labstack.domain;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DNSResponse {
    private Record[] records;
}

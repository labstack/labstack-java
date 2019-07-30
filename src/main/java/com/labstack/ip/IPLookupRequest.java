package com.labstack.ip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class IPLookupRequest {
    private String ip;
}

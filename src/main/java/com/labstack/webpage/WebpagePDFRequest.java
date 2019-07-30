package com.labstack.webpage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class WebpagePDFRequest {
    private String url;
    private String language;
    private Integer ttl;
    private String size;
    private Integer width;
    private Integer height;
    private String orientation;
    private Integer delay;
}

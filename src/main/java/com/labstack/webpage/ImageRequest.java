package com.labstack.webpage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class ImageRequest {
    private String url;
    private String language;
    private Integer ttl;
    private Boolean fullPage;
    private Boolean retina;
    private Integer width;
    private Integer height;
    private Integer delay;
}

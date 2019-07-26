package com.labstack.domain;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Record {
    private String domain;
    private String type;
    private String server;
    private String A;
    private String AAAA;
    private String CNAME;
    private String MX;
    private String NS;
    private String PTR;
    private String serial;
    private Integer refresh;
    private Integer retry;
    private Integer expire;
    private Integer priority;
    private Integer weight;
    private Integer port;
    private String target;
    private String[] TXT;
    private Integer ttl;
    @Json(name = "class")
    private String klass;
    private String[] SPF;
}

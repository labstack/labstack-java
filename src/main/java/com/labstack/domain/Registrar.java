package com.labstack.domain;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Registrar {
    private String id;
    private String name;
    private String url;
    @Json(name = "whois_server")
    private String whoisServer;
}

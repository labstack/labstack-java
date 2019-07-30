package com.labstack.domain;

import java.util.Date;
import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class WhoisResponse {
    private String domain;
    private String id;
    private String status;
    @Json(name = "created_date")
    private String createdDate;
    @Json(name = "updated_date")
    private String updatedDate;
    @Json(name = "expiry_date")
    private String expiryDate;
    @Json(name = "name_servers")
    private String[] nameServers;
    private String dnssec;
    private Registrar registrar;
    private Registrant registrant;
    private Registrant admin;
    private Registrant technical;
    private Registrant billing;
    private String raw;
}

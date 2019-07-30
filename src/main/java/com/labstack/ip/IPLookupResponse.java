package com.labstack.ip;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class IPLookupResponse {
    private String ip;
    private String hostname;
    private String version;
    private String city;
    private String region;
    @Json(name = "region_code")
    private String regionCode;
    private String postal;
    private String country;
    @Json(name = "country_code")
    private String countryCode;
    private Double latitude;
    private Double longitude;
    private Organization organization;
    private Flag flag;
    private String currency;
    @Json(name = "time_zone")
    private TimeZone timeZone;
    private String[] languages;
    private AS as;
    private String[] flags;
}

package com.labstack.domain;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Registrant {
    private String id;
    private String name;
    private String organization;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String fax;
    private String email;
}

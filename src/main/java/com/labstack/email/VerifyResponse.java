package com.labstack.email;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VerifyResponse {
    private String email;
    private String username;
    private String domain;
    private String result;
    private String[] flags;
}

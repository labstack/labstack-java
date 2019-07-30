package com.labstack.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class EmailVerifyRequest {
    private String email;
}

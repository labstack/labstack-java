package com.labstack;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder @Getter @Setter
public class LabStackException extends Exception {
    private Integer statusCode;
    private Integer code;
    private String message;
}

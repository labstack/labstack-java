package com.labstack.ip;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class TimeZone {
    private String id;
    private String name;
    private String abbreviation;
    private Integer offset;
    private Date time;
}

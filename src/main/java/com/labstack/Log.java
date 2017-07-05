package com.labstack;

import com.squareup.moshi.Json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String time;
    @Json(name = "app_id")
    private String appId;
    @Json(name = "app_name")
    private String appName;
    private String[] tags;
    private String level;
    private String message;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

    public Log(String appId, String appName, String[] tags, String level, String message) {
        time = dateFormat.format(new Date());
        this.appId = appId;
        this.appName = appName;
        this.tags = tags;
        this.level = level;
        this.message = message;
    }
}

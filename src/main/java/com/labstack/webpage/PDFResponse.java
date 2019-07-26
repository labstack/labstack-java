package com.labstack.webpage;

import java.util.Date;
import com.squareup.moshi.Json;


public class PDFResponse {
    private String pdf;
    private Boolean cached;
    private Integer took;
    @Json(name = "generated_at")
    private Date generatedAt;
}
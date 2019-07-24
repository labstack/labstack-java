package com.labstack.currency;

import static com.labstack.Client.MOSHI;
import com.squareup.moshi.JsonAdapter;


public class Convert {
    static final JsonAdapter<ConvertResponse> responseJsonAdapter = MOSHI.adapter(ConvertResponse.class);

}

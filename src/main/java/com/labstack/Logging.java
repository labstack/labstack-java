package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Logging {
    private OkHttpClient okHttp;
    private Timer timer;
    private List<Log> logs = Collections.synchronizedList(new ArrayList());
    private String appId;
    private String appName;
    private String[] tags;
    private String level;
    private int batchSize;
    private int dispatchInterval;
    private static final Map<String, Integer> LEVELS = new HashMap<>();

    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    static {
        LEVELS.put("DEBUG", 1);
        LEVELS.put("INFO", 2);
        LEVELS.put("WARN", 3);
        LEVELS.put("ERROR", 4);
    }

    protected Logging() {
    }

    private void dispatch() throws Exception {
        if (logs.size() == 0) {
            return;

        }

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Log.class);
        JsonAdapter<List<Log>> jsonAdapter = moshi.adapter(type);
        String json = jsonAdapter.toJson(logs);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/logging")
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        Response response = okHttp.newCall(request).execute();
        if (response.code() != 204) {
            throw new Exception(String.format("logging: error dispatching logs, status=%d, message=%v", response.code(), response.body()));
        }
    }

    public void setOkHttp(OkHttpClient okHttp) {
        this.okHttp = okHttp;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setDispatchInterval(int dispatchInterval) {
        this.dispatchInterval = dispatchInterval;
    }

    public void debug(String format, Object... args) {
        log(DEBUG, format, args);
    }

    public void info(String format, Object... args) {
        log(INFO, format, args);
    }

    public void warn(String format, Object... args) {
        log(WARN, format, args);
    }

    public void error(String format, Object... args) {
        log(ERROR, format, args);
    }

    public void log(String level, String format, Object... args) {
        if (LEVELS.get(level) < LEVELS.get(this.level)) {
            return;
        }

        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        // TODO: Make it async
                        dispatch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, TimeUnit.SECONDS.toMillis(dispatchInterval));
        }

        String message = String.format(format, args);
        Log log = new Log(appId, appName, tags, this.level, message);
        logs.add(log);

        // Dispatch batch
        if (logs.size() >= batchSize) {
            try {
                // TODO: Make it async
                dispatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logs.clear();
        }
    }
}


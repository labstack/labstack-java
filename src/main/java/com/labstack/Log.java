package com.labstack;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Defines the LabStack log service.
 */
public final class Log {
    protected OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<List<LogEntry>> entriesJsonAdapter = moshi.adapter(Types.newParameterizedType(List.class, LogEntry.class));
    private JsonAdapter<LogException> exceptionJsonAdapter = moshi.adapter(LogException.class);
    private Timer timer;
    private List<LogEntry> entries = Collections.synchronizedList(new ArrayList());
    private String appId;
    private String appName;
    private String[] tags;
    private Level level;
    private int batchSize;
    private int dispatchInterval;

    public enum Level {
        DEBUG, INFO, WARN, ERROR, FATAL, OFF
    }

    protected Log() {
    }

    private void dispatch() throws LogException {
        if (entries.size() == 0) {
            return;
        }

        String json = entriesJsonAdapter.toJson(entries);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/log")
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        try {
            Response response = okHttp.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw exceptionJsonAdapter.fromJson(response.body().source());
            }
        } catch (IOException e) {
            throw new LogException(0, e.getMessage());
        }
        entries.clear();
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

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setDispatchInterval(int dispatchInterval) {
        this.dispatchInterval = dispatchInterval;
    }

    // Logs a message with DEBUG level.
    public void debug(String format, Object... args) {
        log(Level.DEBUG, format, args);
    }

    // Logs a message with INFO level.
    public void info(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    // Logs a message with WARN level.
    public void warn(String format, Object... args) {
        log(Level.WARN, format, args);
    }

    // Logs a message with ERROR level.
    public void error(String format, Object... args) {
        log(Level.ERROR, format, args);
    }

    // Logs a message with FATAL level.
    public void fatal(String format, Object... args) {
        log(Level.FATAL, format, args);
    }

    // Logs a message with log level.
    public void log(Level level, String format, Object... args) {
        if (level.compareTo(this.level) < 0) {
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
        LogEntry entry = new LogEntry(appId, appName, tags, level, message);
        entries.add(entry);

        // Dispatch batch
        if (entries.size() >= batchSize) {
            try {
                // TODO: Make it async
                dispatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class LogEntry {
    private String time;
    @Json(name = "app_id")
    private String appId;
    @Json(name = "app_name")
    private String appName;
    private String[] tags;
    private Log.Level level;
    private String message;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    protected LogEntry(String appId, String appName, String[] tags, Log.Level level, String message) {
        time = dateFormat.format(new Date());
        this.appId = appId;
        this.appName = appName;
        this.tags = tags;
        this.level = level;
        this.message = message;
    }
}

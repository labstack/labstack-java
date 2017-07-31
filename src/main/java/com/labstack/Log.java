package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Defines the LabStack log service.
 */
public final class Log {
    protected OkHttpClient okHttp;
    private JsonAdapter<List<Map<String, Object>>> entriesJsonAdapter = Client.moshi.adapter(Types.newParameterizedType(List.class, Map.class));
    private JsonAdapter<LogException> exceptionJsonAdapter = Client.moshi.adapter(LogException.class);
    private Timer timer;
    private List<Map<String, Object>> entries = Collections.synchronizedList(new ArrayList());
    private Level level;
    private Fields fields = new Fields();
    private int batchSize;
    private int dispatchInterval;

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
        } finally {
            entries.clear();
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Fields getFields() {
        return fields;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setDispatchInterval(int dispatchInterval) {
        this.dispatchInterval = dispatchInterval;
    }

    // Logs a message with DEBUG level.
    public void debug(Fields fields) {
        log(Level.DEBUG, fields);
    }

    // Logs a message with INFO level.
    public void info(Fields fields) {
        log(Level.INFO, fields);
    }

    // Logs a message with WARN level.
    public void warn(Fields fields) {
        log(Level.WARN, fields);
    }

    // Logs a message with ERROR level.
    public void error(Fields fields) {
        log(Level.ERROR, fields);
    }

    // Logs a message with FATAL level.
    public void fatal(Fields fields) {
        log(Level.FATAL, fields);
    }

    // Logs a message with log level.
    public void log(Level level, Fields fields) {
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
                    } catch (LogException e) {
                        System.out.printf("log error: code=%d, message=%s", e.getCode(), e.getMessage());
                    }
                }
            }, 0, TimeUnit.SECONDS.toMillis(dispatchInterval));
        }

        fields.add("time", Client.dateFormat.format(new Date()))
                .add("level", level);
        fields.data.putAll(this.fields.data);
        entries.add(fields.data);

        // Dispatch batch
        if (entries.size() >= batchSize) {
            try {
                // TODO: Make it async
                dispatch();
            } catch (LogException e) {
                System.out.printf("log error: code=%d, message=%s", e.getCode(), e.getMessage());
            }
        }
    }
}


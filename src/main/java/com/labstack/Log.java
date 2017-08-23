package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import okhttp3.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Log implements Thread.UncaughtExceptionHandler {
    private OkHttpClient okHttp;
    private Timer timer;
    private JsonAdapter<List<Map<String, Object>>> entriesJsonAdapter = Client.moshi.adapter(Types.newParameterizedType(List.class, Map.class));
    private JsonAdapter<LogException> exceptionJsonAdapter = Client.moshi.adapter(LogException.class);
    private List<Map<String, Object>> entries = Collections.synchronizedList(new ArrayList());
    private Level level;
    private Fields fields = new Fields();
    private int batchSize;
    private int dispatchInterval;
    protected Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandle;

    protected Log(Client client) {
        this.okHttp = client.okHttp;
        level = Level.INFO;
        batchSize = 60;
        dispatchInterval = 60;

        defaultUncaughtExceptionHandle = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    private void dispatch(final LogCallback callback) throws LogException {
        if (entries.size() == 0) {
            return;
        }

        String json = entriesJsonAdapter.toJson(entries);
        Request request = new Request.Builder()
                .url(Client.API_URL + "/log")
                .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                .build();
        Call call = okHttp.newCall(request);

        if (callback == null) {
            try {
                Response response = call.execute();
                if (!response.isSuccessful()) {
                    throw exceptionJsonAdapter.fromJson(response.body().source());
                }
            } catch (IOException e) {
                throw new LogException(0, e.getMessage());
            } finally {
                entries.clear();
            }
        } else {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    entries.clear();
                    callback.onError(new LogException(0, e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    entries.clear();
                    if (!response.isSuccessful()) {
                        callback.onError(exceptionJsonAdapter.fromJson(response.body().source()));
                    }
                    callback.onSuccess();
                }
            });
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

    public void debug(Fields fields) {
        log(Level.DEBUG, fields);
    }

    public void debug(Fields fields, LogCallback callback) {
        log(Level.DEBUG, fields, callback);
    }

    public void info(Fields fields) {
        log(Level.INFO, fields);
    }

    public void info(Fields fields, LogCallback callback) {
        log(Level.INFO, fields, callback);
    }

    public void warn(Fields fields) {
        log(Level.WARN, fields);
    }

    public void warn(Fields fields, LogCallback callback) {
        log(Level.WARN, fields, callback);
    }

    public void error(Fields fields) {
        log(Level.ERROR, fields);
    }

    public void error(Fields fields, LogCallback callback) {
        log(Level.ERROR, fields, callback);
    }

    public void fatal(Fields fields) {
        log(Level.FATAL, fields);
    }

    public void fatal(Fields fields, LogCallback callback) {
        log(Level.FATAL, fields, callback);
    }

    // Logs a message with log level.
    private void log(final Level level, Fields fields, final LogCallback callback) {
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
                        dispatch(callback);
                    } catch (LogException e) {
                        System.out.printf("log error: code=%d, message=%s", e.getCode(), e.getMessage());
                    }
                }
            }, 0, TimeUnit.SECONDS.toMillis(dispatchInterval));
        }

        fields.add("time", new Date())
                .add("level", level);
        fields.data.putAll(this.fields.data);
        entries.add(fields.data);

        // Dispatch batch
        if (level == Level.FATAL || entries.size() >= batchSize) try {
            dispatch(callback);
        } catch (LogException e) {
            System.out.printf("log error: code=%d, message=%s", e.getCode(), e.getMessage());
        }
    }

    private void log(final Level level, Fields fields) {
        log(level, fields, null);
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        fatal(new Fields()
                .add("message", throwable.getMessage())
                .add("stack_trace", getStackTrace(throwable)));
        defaultUncaughtExceptionHandle.uncaughtException(thread, throwable);
    }
}


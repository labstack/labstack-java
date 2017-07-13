package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Defines the LabStack email service.
 */
public class Email {
    protected OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe()).build();
    private JsonAdapter<EmailMessage> messageJsonAdapter = moshi.adapter(EmailMessage.class);
    private JsonAdapter<EmailException> exceptionJsonAdapter = moshi.adapter(EmailException.class);

    private void addFiles(List<String> files, List<EmailFile> emailFiles) throws IOException {
        for (String file : files) {
            Path path = Paths.get(file);
            String type = Files.probeContentType(path);
            String content = DatatypeConverter.printBase64Binary(Files.readAllBytes(path));
            EmailFile emailFile = new EmailFile(path.getFileName().toString(), type, content);
            emailFiles.add(emailFile);
        }
    }

    public EmailMessage send(EmailMessage message) throws EmailException {
        try {
            message.addFiles();
            String json = messageJsonAdapter.toJson(message);
            Request request = new Request.Builder()
                    .url(Client.API_URL + "/email")
                    .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                    .build();
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                return messageJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new EmailException(0, e.getMessage());
        }
    }
}


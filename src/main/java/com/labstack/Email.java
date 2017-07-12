package com.labstack;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Defines the LabStack email service.
 */
public class Email {
    protected OkHttpClient okHttp;
    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<EmailMessage> messageJsonAdapter = moshi.adapter(EmailMessage.class);
    private JsonAdapter<EmailStatus> statusJsonAdapter = moshi.adapter(EmailStatus.class);
    private JsonAdapter<EmailException> exceptionJsonAdapter = moshi.adapter(EmailException.class);

    private void addFile(List<String> files, List<EmailFile> emailFiles) throws IOException {
        for (String file : files) {
            Path path = Paths.get(file);
            String content = DatatypeConverter.printBase64Binary(Files.readAllBytes(path));
            String type = Files.probeContentType(path);
            EmailFile emailFile = new EmailFile(path.getFileName().toString(), type, content);
            emailFiles.add(emailFile);
        }
    }

    public EmailStatus send(EmailMessage message) throws EmailException {
        try {
            addFile(message.inlines, message.inlineFiles);
            addFile(message.attachments, message.attachmentFiles);
            String json = messageJsonAdapter.toJson(message);
            Request request = new Request.Builder()
                    .url(Client.API_URL + "/email")
                    .post(RequestBody.create(Client.MEDIA_TYPE_JSON, json))
                    .build();
            Response response = okHttp.newCall(request).execute();
            if (response.isSuccessful()) {
                return statusJsonAdapter.fromJson(response.body().source());
            }
            throw exceptionJsonAdapter.fromJson(response.body().source());
        } catch (IOException e) {
            throw new EmailException(0, e.getMessage());
        }
    }
}


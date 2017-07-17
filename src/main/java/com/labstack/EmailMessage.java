package com.labstack;

import com.squareup.moshi.Json;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the email message.
 */
public class EmailMessage {
    private String time;
    private String to;
    private String from;
    private String subject;
    private String body;
    @Json(name = "inlines")
    private List<EmailFile> inlineFiles = new ArrayList<>();
    @Json(name = "attachments")
    private List<EmailFile> attachmentFiles = new ArrayList<>();
    private transient List<String> inlines = new ArrayList<>();
    private transient List<String> attachments = new ArrayList<>();
    private String status;

    public EmailMessage(String to, String from, String subject) {
        this.to = to;
        this.from = from;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    protected void addFiles() throws IOException {
        for (String path : inlines) {
            inlineFiles.add(EmailFile.fromPath(path));
        }
        for (String path : attachments) {
            attachmentFiles.add(EmailFile.fromPath(path));
        }
    }

    public void addInline(String path) {
        inlines.add(path);
    }

    public void addAttachment(String path) {
        attachments.add(path);
    }
}

class EmailFile {
    private String name;
    private String type;
    private String content;

    public static EmailFile fromPath(String path) throws IOException {
        Path p = Paths.get(path);
        return new EmailFile(p.getFileName().toString(), DatatypeConverter.printBase64Binary(Files.readAllBytes(p)));
    }

    private EmailFile(String name, String content) {
        this.name = name;
        this.content = content;
    }
}

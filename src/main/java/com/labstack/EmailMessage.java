package com.labstack;

import com.squareup.moshi.Json;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Defines the email message.
 */
public class EmailMessage {
    private String from;
    private String to;
    private String subject;
    private String body;
    @Json(name = "inlines")
    protected List<EmailFile> inlineFiles = new ArrayList<>();
    @Json(name = "attachments")
    protected List<EmailFile> attachmentFiles = new ArrayList<>();
    protected transient List<String> inlines = new ArrayList<>();
    protected transient List<String> attachments = new ArrayList<>();
    private boolean submitted;
    private boolean delivered;
    private boolean clicked;
    private boolean bounced;
    @Json(name = "created_at")
    private Date createdAt;
    @Json(name = "updated_at")
    private Date updatedAt;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isBounced() {
        return bounced;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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
        return new EmailFile(p.getFileName().toString(), Files.probeContentType(p), DatatypeConverter.printBase64Binary(Files.readAllBytes(p)));
    }

    public EmailFile(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }
}

package com.fpo.app.api.model;

public class PostMessage {
    private String message;
    private String attachmentId;

    public PostMessage(String message, String attachmentId) {
        this.message = message;
        this.attachmentId = attachmentId;
    }

    public PostMessage(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

}

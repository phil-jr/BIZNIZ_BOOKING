package com.fpo.app.api.model;

public class PostMessage {
    private String businessUserName;
    private String message;
    private String dateTime;
    private String attachmentId;

    public PostMessage(){}

    public PostMessage(String businessUserName, String message, String dateTime, String attachmentId) {
        this.businessUserName = businessUserName;
        this.message = message;
        this.dateTime = dateTime;
        this.attachmentId = attachmentId;
    }

    public String getBusinessUserName() {
        return businessUserName;
    }

    public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    
    
}

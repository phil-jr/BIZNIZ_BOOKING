package com.gm.fgcf.api.model;

public class UserSession {
    public String userId;
    public String sessionId;
    public String sessionTimestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTimestamp() {
        return sessionTimestamp;
    }

    public void setSessionTimestamp(String sessionTimestamp) {
        this.sessionTimestamp = sessionTimestamp;
    }
}

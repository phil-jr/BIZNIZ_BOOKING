package com.gm.fgcf.api.model;

import java.util.ArrayList;

public class SignInReturn{
    public SignInReturn(){}
    private String username;
    private String sessionId;
    private ArrayList<String> errors;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public ArrayList<String> getErrors() {
        return errors;
    }
    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    } 

    

}


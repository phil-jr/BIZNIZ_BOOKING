package com.gm.fgcf.api.model.returnModel;


import java.util.ArrayList;

public class GenericReturn {
    private String message;
    private ArrayList<String> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
}

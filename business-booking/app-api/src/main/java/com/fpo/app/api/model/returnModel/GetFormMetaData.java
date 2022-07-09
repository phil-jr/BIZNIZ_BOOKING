package com.fpo.app.api.model.returnModel;

import com.fpo.app.api.model.FormMetadataPiece;

import java.util.ArrayList;

public class GetFormMetaData {
    private String formName;
    private ArrayList<FormMetadataPiece> formInputData;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public ArrayList<FormMetadataPiece> getFormInputData() {
        return formInputData;
    }

    public void setFormInputData(ArrayList<FormMetadataPiece> formInputData) {
        this.formInputData = formInputData;
    }
}

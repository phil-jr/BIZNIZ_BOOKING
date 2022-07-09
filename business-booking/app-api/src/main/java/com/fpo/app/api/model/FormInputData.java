package com.fpo.app.api.model;

import com.fpo.app.api.model.ScheduleMetadata.ScheduleMetadata;

import java.util.ArrayList;

public class FormInputData {

    private String formName;
    private ArrayList<FormInputDatapiece> formInputData;
    private ScheduleMetadata scheduleMetadata;

    public ArrayList<FormInputDatapiece> getFormInputData() {
        return formInputData;
    }

    public void setFormInputData(ArrayList<FormInputDatapiece> formInputData) {
        this.formInputData = formInputData;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public ScheduleMetadata getScheduleMetadata() {
        return scheduleMetadata;
    }

    public void setScheduleMetadata(ScheduleMetadata scheduleMetadata) {
        this.scheduleMetadata = scheduleMetadata;
    }
}

package com.gm.fgcf.api.model;

import java.util.ArrayList;

public class AppointmentBooking {
    private String timeSlotBegin;
    private String timeSlotEnd;
    private String formLinkName;
    private String business;
    private ArrayList<FormData> formData;

    AppointmentBooking() {}

    public String getTimeSlotBegin() {
        return timeSlotBegin;
    }

    public void setTimeSlotBegin(String timeSlotBegin) {
        this.timeSlotBegin = timeSlotBegin;
    }

    public String getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public void setTimeSlotEnd(String timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
    }

    public String getFormLinkName() {
        return formLinkName;
    }

    public void setFormLinkName(String formLinkName) {
        this.formLinkName = formLinkName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public ArrayList<FormData> getFormData() {
        return formData;
    }

    public void setFormData(ArrayList<FormData> formData) {
        this.formData = formData;
    }

    
    
}

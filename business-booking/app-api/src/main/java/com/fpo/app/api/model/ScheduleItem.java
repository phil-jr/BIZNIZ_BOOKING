package com.fpo.app.api.model;

public class ScheduleItem {
    private String scheduleItemId;
    private String formId;
    private String customerId;
    private String timeSlotBegin;
    private String timeSlotEnd;
    private boolean available;

    public ScheduleItem(){}

    public String getScheduleItemId() {
        return scheduleItemId;
    }

    public void setScheduleItemId(String scheduleItemId) {
        this.scheduleItemId = scheduleItemId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

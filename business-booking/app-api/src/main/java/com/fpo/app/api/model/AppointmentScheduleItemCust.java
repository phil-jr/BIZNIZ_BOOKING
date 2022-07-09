package com.fpo.app.api.model;

public class AppointmentScheduleItemCust {
    private String timeSlotBegin;
    private String timeSlotEnd;
    private String available;

    public AppointmentScheduleItemCust(){}

    public AppointmentScheduleItemCust(String timeSlotBegin, String timeSlotEnd, String available) {
        this.timeSlotBegin = timeSlotBegin;
        this.timeSlotEnd = timeSlotEnd;
        this.available = available;
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
    public String getAvailable() {
        return available;
    }
    public void setAvailable(String available) {
        this.available = available;
    }
    
}

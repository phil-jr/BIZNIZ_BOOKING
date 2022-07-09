package com.gm.fgcf.api.model.ScheduleMetadata;

import java.util.ArrayList;

public class ScheduleMetadata {
    private String dayStart;
    private String dayEnd;
    private ArrayList<ExceptionDay> exceptionDay;
    private ArrayList<String> daysOfWeek;
    private ArrayList<StartEnd> sunTimes;
    private ArrayList<StartEnd> monTimes;
    private ArrayList<StartEnd> tueTimes;
    private ArrayList<StartEnd> wedTimes;
    private ArrayList<StartEnd> thuTimes;
    private ArrayList<StartEnd> friTimes;
    private ArrayList<StartEnd> satTimes;

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public ArrayList<ExceptionDay> getExceptionDay() {
        return exceptionDay;
    }

    public void setExceptionDay(ArrayList<ExceptionDay> exceptionDay) {
        this.exceptionDay = exceptionDay;
    }

    public ArrayList<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(ArrayList<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public ArrayList<StartEnd> getSunTimes() {
        return sunTimes;
    }

    public void setSunTimes(ArrayList<StartEnd> sunTimes) {
        this.sunTimes = sunTimes;
    }

    public ArrayList<StartEnd> getMonTimes() {
        return monTimes;
    }

    public void setMonTimes(ArrayList<StartEnd> monTimes) {
        this.monTimes = monTimes;
    }

    public ArrayList<StartEnd> getTueTimes() {
        return tueTimes;
    }

    public void setTueTimes(ArrayList<StartEnd> tueTimes) {
        this.tueTimes = tueTimes;
    }

    public ArrayList<StartEnd> getWedTimes() {
        return wedTimes;
    }

    public void setWedTimes(ArrayList<StartEnd> wedTimes) {
        this.wedTimes = wedTimes;
    }

    public ArrayList<StartEnd> getThuTimes() {
        return thuTimes;
    }

    public void setThuTimes(ArrayList<StartEnd> thuTimes) {
        this.thuTimes = thuTimes;
    }

    public ArrayList<StartEnd> getFriTimes() {
        return friTimes;
    }

    public void setFriTimes(ArrayList<StartEnd> friTimes) {
        this.friTimes = friTimes;
    }

    public ArrayList<StartEnd> getSatTimes() {
        return satTimes;
    }

    public void setSatTimes(ArrayList<StartEnd> satTimes) {
        this.satTimes = satTimes;
    }
}




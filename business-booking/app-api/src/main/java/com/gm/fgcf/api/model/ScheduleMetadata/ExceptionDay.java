package com.gm.fgcf.api.model.ScheduleMetadata;

import java.util.ArrayList;

public class ExceptionDay {
    private String exceptionDay;
    private ArrayList<StartEnd> exceptionTimes;

    public String getExceptionDay() {
        return exceptionDay;
    }

    public void setExceptionDay(String exceptionDay) {
        this.exceptionDay = exceptionDay;
    }

    public ArrayList<StartEnd> getExceptionTimes() {
        return exceptionTimes;
    }

    public void setExceptionTimes(ArrayList<StartEnd> exceptionTimes) {
        this.exceptionTimes = exceptionTimes;
    }
}

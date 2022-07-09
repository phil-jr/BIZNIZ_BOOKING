package com.gm.fgcf.api.model.KafkaObjects;

public class KBookedAppointment {
    public String process;
    public Object customer;
    public Object bookingDetails;
    public KBookedAppointment(String process, Object customer, Object bookingDetails) {
        this.process = process;
        this.customer = customer;
        this.bookingDetails = bookingDetails;
    }
}

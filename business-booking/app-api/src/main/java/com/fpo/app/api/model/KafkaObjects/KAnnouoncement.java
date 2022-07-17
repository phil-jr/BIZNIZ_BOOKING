package com.fpo.app.api.model.KafkaObjects;

public class KAnnouoncement {
    public String process;
    public Object announcement;
    public KAnnouoncement(String process, Object announcement) {
        this.process = process;
        this.announcement = announcement;
    }
}

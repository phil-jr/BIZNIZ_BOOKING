package com.fpo.app.api.model.KafkaObjects;

public class KFollowPage {
    public String process;
    public Object obj;
    public KFollowPage(String process, Object obj) {
        this.process = process;
        this.obj = obj;
    }
}

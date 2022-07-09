package com.gm.fgcf.api.model;

public class KafkaObject {
    private String topic;
    private String key;
    private Object payload;

    public KafkaObject(String topic, String key, Object payload) {
        this.topic = topic;
        this.key = key;
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Object getPayload() {
        return payload;
    }
    public void setPayload(Object payload) {
        this.payload = payload;
    }


    
}

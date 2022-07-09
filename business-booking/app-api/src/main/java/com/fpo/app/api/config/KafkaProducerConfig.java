package com.fpo.app.api.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpo.app.api.model.returnModel.GenericReturn;
import com.fpo.app.api.util.ExistsValidateGet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//GITIGNORE FOR HOSTNAME
@Configuration
public class KafkaProducerConfig {

    @Autowired
    ExistsValidateGet existsValidateGet;

    @Bean
    public KafkaProducer<String, String> kafakProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public void pushToKafka(KafkaProducer<String, String> producer, String topic, Object message, String identifier, String identifierValue) throws JsonProcessingException, JSONException{ 
        if( message != null ){
            ObjectMapper objectMapper = new ObjectMapper();
            String key = existsValidateGet.getBusinessSession(identifier, identifierValue);
            System.out.println(objectMapper.writeValueAsString(message));
            if(key != null) {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, objectMapper.writeValueAsString(message));
                producer.send(record);
            }
        }
    }
    
}

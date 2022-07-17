package com.fpo.app.api.config;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import com.fpo.app.api.service.CustomerService;
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

    @Autowired
    CustomerService customerService;

    @Bean
    public KafkaProducer<String, String> kafakProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public void pushToKafkaBusiness(String topic, Object message, String identifier, String identifierValue) throws JsonProcessingException, JSONException{ 
        if( message != null ) {
            List<String> customerSessions = customerService.getCustomerSessionsByBusiness("");
            ObjectMapper objectMapper = new ObjectMapper();
            String key = existsValidateGet.getBusinessSession(identifier, identifierValue);
            System.out.println(objectMapper.writeValueAsString(message));
            if(key != null) {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, objectMapper.writeValueAsString(message));
                this.kafakProducer().send(record);
            }
        }
    }

    public void pushToKafkaAnnouncement(String topic, Object message, String businessSession, String identifierValue) throws JsonProcessingException, JSONException{ 
        if(message != null) {
            List<String> custSessions = customerService.getCustomerSessionsByBusiness(businessSession);
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < custSessions.size(); i++) {
                System.out.println("======>" + custSessions.get(i));
                String key = custSessions.get(i);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, objectMapper.writeValueAsString(message));
                this.kafakProducer().send(record);
            }
        }
    }
    
}

package com.fpo.app.api.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.fpo.app.api.config.KafkaProducerConfig;
import com.fpo.app.api.constants.SqlConstants;
import com.fpo.app.api.model.PostMessage;
import com.fpo.app.api.model.KafkaObjects.KAnnouoncement;
import com.fpo.app.api.util.RandomUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AnnouncementService {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    KafkaProducer<String, String> kafkaProducer;

    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    public ResponseEntity<Object> postAnnouncement(String sessionId, String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PostMessage postMessage = objectMapper.readValue(payload, PostMessage.class);

        ArrayList<String> errors = new ArrayList<String>();
        String announcementId = randomUtil.getSaltString(16);

        MapSqlParameterSource params = new MapSqlParameterSource();
        String attachmentId =  postMessage.getAttachmentId() == "null" ? null : postMessage.getAttachmentId();
        params.addValue("announcementId", announcementId);
        params.addValue("sessionId", sessionId);
        params.addValue("message", postMessage.getMessage());
        params.addValue("attachmentId", attachmentId);
        KAnnouoncement kAnnouoncement = new KAnnouoncement("announcement", postMessage);
        kafkaProducerConfig.pushToKafkaAnnouncement("TEST-TOPIC", kAnnouoncement, sessionId, "test-feed");

        try {
            namedParameterJdbcTemplate.update(SqlConstants.POST_ANNOUNCEMENT, params);
            return ResponseEntity.ok().body(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Message\":\"Post unsuccessful\", \"Errors\":"+ objectMapper.writeValueAsString(errors) +"}");
        }
    }
}

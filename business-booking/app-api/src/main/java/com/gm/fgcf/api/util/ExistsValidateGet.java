package com.gm.fgcf.api.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.fgcf.api.model.Business;
import com.gm.fgcf.api.model.Customer;

@Service
public class ExistsValidateGet {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Boolean businessEmail (String email) {
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT email FROM Businesses WHERE email = ?", new Object[]{email}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean businessUsername (String username) {
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT username FROM Businesses WHERE username = ?", new Object[]{username}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean customerEmail (String email) {
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT email FROM Customers WHERE email = ?", new Object[]{email}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean customerUsername (String username) {
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT username FROM customers WHERE username = ?", new Object[]{username}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean doesOwnForm(String formId, String sessionId){
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT formId FROM forms WHERE forms.businessId = (SELECT businessId FROM businessSession WHERE sessionId = ?) AND formId = ?", new Object[]{sessionId, formId}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean doesOwnFormByLinkName(String formLinkName, String sessionId){
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT formId FROM forms WHERE forms.businessId = (SELECT businessId FROM businessSession WHERE sessionId = ?) AND formLinkName = ?", new Object[]{sessionId, formLinkName}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean appointmentIsAvailable(String scheduleId){
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT * FROM scheduling WHERE scheduleId = ? AND available = true", new Object[]{scheduleId}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public Boolean isBusinessUser(String businessUsername, String sessionId){
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT * FROM businesssession WHERE sessionId = ? AND businessId = (SELECT businessId FROM businesses WHERE username = ?)", new Object[]{sessionId, businessUsername}));
        return ((ArrayList<Object>) obj.get(0)).size() > 0;
    }

    public String getBusinessSession(String identifier, String identifierValue) throws JsonProcessingException, JSONException{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if(identifier.equals("username")){
                List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT sessionId FROM businesssession WHERE businessId = (SELECT businessId FROM businesses WHERE username = ?)", new Object[]{identifierValue}));
                JSONObject returObj = new JSONObject(objectMapper.writeValueAsString(((ArrayList<Object>) obj.get(0)).get(0)));
                return returObj.get("sessionId").toString();
            } else if (identifier.equals("businessId")){
                List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT sessionId FROM businesssession WHERE businessId = ?", new Object[]{identifierValue}));
                JSONObject returObj = new JSONObject(objectMapper.writeValueAsString(((ArrayList<Object>) obj.get(0)).get(0)));
                return returObj.get("sessionId").toString();
            } 
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerBySession(String customerSessionId){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String sql = "SELECT firstName, lastName, email, username, profilePictureAttachment, signUpDate FROM customers "
                       + "WHERE customerId = (SELECT customerId FROM customersession WHERE sessionId = ?)";
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{customerSessionId}));
            Customer[] customers = objectMapper.readValue(objectMapper.writeValueAsString(obj.get(0)), Customer[].class);
            return customers[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Business getBusinessBySession(String businessSessionId){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String sql = "SELECT name, email, phoneNumber, username, description, address, categoryId, profilePictureAttachment, signUpDate FROM businesses "
                       + "WHERE businessId = (SELECT businessId FROM businesssession WHERE sessionId = ?)";
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{businessSessionId}));
            Business[] businesses = objectMapper.readValue(objectMapper.writeValueAsString(obj.get(0)), Business[].class);
            return businesses[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

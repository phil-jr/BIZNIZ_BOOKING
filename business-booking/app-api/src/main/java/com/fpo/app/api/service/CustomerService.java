package com.fpo.app.api.service;

import com.fpo.app.api.config.KafkaProducerConfig;
import com.fpo.app.api.model.*;
import com.fpo.app.api.model.KafkaObjects.KFollowPage;
import com.fpo.app.api.model.returnModel.GenericReturn;
import com.fpo.app.api.util.ExistsValidateGet;
import com.fpo.app.api.util.RandomUtil;
import com.fpo.app.api.constants.SqlConstants;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RandomUtil randomUtil;

    @Autowired
    ExistsValidateGet existsValidateGet;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    KafkaProducer<String, String> kafkaProducer;

    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ResponseEntity<Object> customerSignUp(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer csuf = objectMapper.readValue(payload, Customer.class);
        ArrayList<String> errors = new ArrayList<String>();

        if (existsValidateGet.customerEmail(csuf.getEmail())){
            errors.add("Email already exists");
        }

        if (existsValidateGet.customerUsername(csuf.getUsername())){
            errors.add("Username already exists");
        }

        try {
            if (errors.size() == 0) {
                String sql = "INSERT INTO Customers (CustomerId, FirstName, LastName, Email, Username, Password, ProfilePictureAttachment, SignUpDate) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
                String password = passwordEncoder.encode(csuf.getPassword());
                String customerId = randomUtil.getSaltString(16);
                jdbcTemplate.update(sql, new Object[]{customerId, csuf.getFirstName(), csuf.getLastName(), csuf.getEmail(), csuf.getUsername(), password, csuf.getProfilePictureAttachment()});
                return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Sign up successful\"}");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Sign up unsuccessful\", \"Errors\":"+ objectMapper.writeValueAsString(errors) +"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("An unexpected error occured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Message\":\"Sign up unsuccessful\", \"Errors\":"+ objectMapper.writeValueAsString(errors) +"}");
        }
    }

    public ResponseEntity<Object> customerSignIn(String payload) throws IOException {
        
        SignInReturn signInReturn = new SignInReturn();
        ArrayList<String> errors = new ArrayList<String>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SignInForm csifUserGiven = objectMapper.readValue(payload, SignInForm.class);
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT username, password FROM Customers WHERE username = ?", new Object[]{csifUserGiven.getUsername()}));
            ArrayList<String> signInUserArr = (ArrayList<String>) obj.get(0);
            if (signInUserArr.size() > 0) {
                SignInForm csifDbGiven = objectMapper.readValue(objectMapper.writeValueAsString(signInUserArr.get(0)), SignInForm.class);
                if (passwordEncoder.matches((CharSequence) csifUserGiven.getPassword(), csifDbGiven.getPassword())) {
                    String sessionId = randomUtil.getSaltString(32);
                    String sessionSql = "INSERT INTO CustomerSession VALUES (?, (SELECT CustomerId FROM Customers WHERE username = ?), NOW());";
                    jdbcTemplate.update(sessionSql, new Object[]{sessionId, csifUserGiven.getUsername()});
                    signInReturn.setUsername(csifDbGiven.getUsername());
                    signInReturn.setSessionId(sessionId);
                    signInReturn.setErrors(errors);
                    return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(signInReturn));
                } else {
                    errors.add("Wrong username or password");
                    signInReturn.setErrors(errors);
                    return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(signInReturn));
                }
            } else {
                errors.add("Wrong username or password");
                signInReturn.setErrors(errors);
                return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(signInReturn));
            }
        } catch (Exception e) {
            errors.add("An unexpected error occurred");
            signInReturn.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(signInReturn));
        }
    }

    public ResponseEntity<Object> customerSearch(String search) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String sql = "SELECT firstName, lastName, email, username, profilePictureAttachment, signUpDate FROM customers "
                   + "WHERE firstName LIKE ? OR lastName LIKE ? OR email LIKE ? OR username LIKE ? OR signUpDate LIKE ?";
        search = "%" + search + "%";
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{search, search, search, search, search}));
        Customer[] customers = objectMapper.readValue(objectMapper.writeValueAsString(obj.get(0)), Customer[].class);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(customers));
    }

    public ResponseEntity<Object> customerBusinessFollow(String payload, String customerSessionId) throws IOException {
        GenericReturn gr = new GenericReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> errors = new ArrayList<String>();
        try {
            //TODO Change this so it can retrieve the username using JSONObject
            JSONObject payloadObj = new JSONObject(payload);
            String businessUsername = payloadObj.get("businessUsername").toString();
            String sql = "INSERT INTO businesscustomer VALUES ((SELECT businessId FROM businesses WHERE username = ?), (SELECT customerId FROM customersession WHERE sessionId = ?), NOW())";
            jdbcTemplate.update(sql, new Object[]{businessUsername, customerSessionId});
            gr.setMessage("Follow successful");
            gr.setErrors(errors);

            //Send to kafka
            Customer customer = existsValidateGet.getCustomerBySession(customerSessionId);
            kafkaProducerConfig.pushToKafkaBusiness("TEST-TOPIC", new KFollowPage("follow", customer), "username", businessUsername);

            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(gr));
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("Follow unsuccessful");
            gr.setMessage("An unexpected error occured");
            gr.setErrors(errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        }
    }

    public ResponseEntity<Object> customerBusinessUnfollow(String payload, String customerSessionId) throws IOException {
        GenericReturn gr = new GenericReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> errors = new ArrayList<String>();
        try {
            //TODO Change this so it can retrieve the username using JSONObject
            JSONObject payloadObj = new JSONObject(payload);
            String businessUsername = payloadObj.get("businessUsername").toString();
            String sql = "DELETE FROM businesscustomer WHERE businessId = (SELECT businessId FROM businesses WHERE username = ?) AND customerId = (SELECT customerId FROM customersession WHERE sessionId = ?)";
            jdbcTemplate.update(sql, new Object[]{businessUsername, customerSessionId});
            gr.setMessage("Unfollow successful");
            gr.setErrors(errors);

             //Send to kafka
             Customer customer = existsValidateGet.getCustomerBySession(customerSessionId);
             kafkaProducerConfig.pushToKafkaBusiness("TEST-TOPIC", new KFollowPage("unfollow", customer), "username", businessUsername);

            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(gr));
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("Follow unsuccessful");
            gr.setMessage("An unexpected error occured");
            gr.setErrors(errors);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        }
    }

    public ResponseEntity<Object> customerSignOut(String payload) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserSession customerSession = objectMapper.readValue(payload, UserSession.class);
            String sql = "DELETE FROM BusinessSession WHERE SessionId = ? AND BusinessId = ?";
            jdbcTemplate.update(sql, new Object[]{customerSession.getSessionId(), customerSession.getUserId()});
            return ResponseEntity.status(HttpStatus.OK).body("{\"Message\":\"Sign out successful\", \"Errors\":[]}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Message\":\"Sign out unsuccessful\", \"Errors\":[\"An unexpected error occurred\"]}");
        }
    }

    public ResponseEntity<Object> getCustomerProfile(String customer){
        return ResponseEntity.status(HttpStatus.OK).body("HELLO " + customer);
    }

    public List<String> getCustomerSessionsByBusiness(String businessSessionId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sessionId", businessSessionId);
        ArrayList<String> customerSession = (ArrayList<String>) namedParameterJdbcTemplate.query(SqlConstants.BUSINESS_CUSTOMER_SESSIONS, params, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("sessionId");
            }
        });
        return customerSession;
    }

}

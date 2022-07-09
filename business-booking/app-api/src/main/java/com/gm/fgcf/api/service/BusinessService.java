package com.gm.fgcf.api.service;

import com.gm.fgcf.api.model.*;
import com.gm.fgcf.api.model.returnModel.GenericReturn;
import com.gm.fgcf.api.util.ExistsValidateGet;
import com.gm.fgcf.api.util.RandomUtil;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BusinessService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RandomUtil randomUtil;

    @Autowired
    ExistsValidateGet existsValidate;

    @Autowired
    AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> businessSignUp(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Business bsui = objectMapper.readValue(payload, Business.class);
        ArrayList<String> errors = new ArrayList<String>();

        if(existsValidate.businessEmail(bsui.getEmail())){
            errors.add("Email already exists");
        }

        if (existsValidate.businessUsername(bsui.getUsername())){
            errors.add("Username already exists");
        }

        try {
            if (errors.size() == 0) {
                Address address = bsui.getAddressObj();
                String addressId = randomUtil.getSaltString(16);
                String sqlAddress = "INSERT INTO addresses VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sqlAddress, new Object[]{addressId, address.getAddressLine1(), address.getAddressLine2(), address.getAddressLine3(), address.getCity(), address.getState(), address.getPostalCode(), address.getCountry()});

                String sql = "INSERT INTO businesses VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
                String password = passwordEncoder.encode(bsui.getPassword());
                String businessId = randomUtil.getSaltString(16);
                jdbcTemplate.update(sql, new Object[]{businessId, bsui.getName(), bsui.getEmail(), bsui.getPhoneNumber(), bsui.getUsername(), password, bsui.getDescription(), addressId, bsui.getCategoryId(), bsui.getProfilePictureAttachment()});
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

    public ResponseEntity<Object> businessSignIn(String payload) throws IOException {        
        SignInReturn signInReturn = new SignInReturn();
        ArrayList<String> errors = new ArrayList<String>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignInForm bsifUserGiven = objectMapper.readValue(payload, SignInForm.class);
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT username, password FROM businesses WHERE username = ?", new Object[]{bsifUserGiven.getUsername()}));
            ArrayList<String> signInUserArr = (ArrayList<String>) obj.get(0);
            if (signInUserArr.size() > 0) {
                SignInForm bsifDbGiven = objectMapper.readValue(objectMapper.writeValueAsString(signInUserArr.get(0)), SignInForm.class);
                if (passwordEncoder.matches((CharSequence) bsifUserGiven.getPassword(), bsifDbGiven.getPassword())) {
                    String sessionId = randomUtil.getSaltString(32);
                    String sessionSql = "INSERT INTO businesssession VALUES (?, (SELECT businessId FROM businesses WHERE username = ?), NOW());";
                    jdbcTemplate.update(sessionSql, new Object[]{sessionId, bsifUserGiven.getUsername()});
                    signInReturn.setUsername(bsifDbGiven.getUsername());
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(signInReturn));
        }
    }

    public ResponseEntity<Object> businessSearch(String search) throws IOException {
        GenericReturn gr = new GenericReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String sql = "SELECT businessId, businesses.name, email, phoneNumber, username, businesses.description, address, categories.name as 'categoryName', categories.description FROM businesses "
                    + "LEFT JOIN categories ON businesses.categoryId = categories.categoryId "
                    + "WHERE businesses.name LIKE ? OR email LIKE ? OR phoneNumber LIKE ? OR username LIKE ? OR businesses.description LIKE ? OR address LIKE ? OR categories.name LIKE ? OR categories.description LIKE ?";
            search = "%" + search + "%";
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{search, search, search, search, search, search, search, search}));
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString((ArrayList<String>) obj.get(0)));
        } catch (Exception e) {
            gr.setMessage("Search unsuccessful");
            gr.setErrors(new ArrayList<String>(Arrays.asList("an unexpected error occurred")));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        }
    }

    public ResponseEntity<Object> getIsCustomerFollowing(String businessUsername, String customerSession) throws IOException {
        GenericReturn gr = new GenericReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String sql = "SELECT * FROM businesscustomer WHERE businessId = (SELECT businessId FROM businesses WHERE username = ?) AND customerId = (SELECT customerId FROM customersession WHERE sessionId = ?)";
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{businessUsername, customerSession}));
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(new TrueFalse(((ArrayList<Object>)obj.get(0)).size()>0)));
        } catch (Exception e) {
            e.printStackTrace();
            gr.setMessage("Something went wrong here!");
            gr.setErrors(new ArrayList<String>(Arrays.asList("an unexpected error occurred")));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        }
    }

    public ResponseEntity<Object> businessSignOut(String payload) throws IOException {
        GenericReturn gr = new GenericReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserSession businessSession = objectMapper.readValue(payload, UserSession.class);
            String sql = "DELETE FROM businesssession WHERE sessionId = ? AND businessId = ?";
            jdbcTemplate.update(sql, new Object[]{businessSession.getSessionId(), businessSession.getUserId()});
            gr.setMessage("Sign out successful");
            gr.setErrors(new ArrayList<String>());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        } catch (Exception e) {
            gr.setMessage("Sign out unsuccessful");
            gr.setErrors(new ArrayList<String>(Arrays.asList("an unexpected error occurred")));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectMapper.writeValueAsString(gr));
        }
    }

    public ResponseEntity<Object> getBusinessCustomers(String busnessUsername) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String sql = "SELECT firstName, lastName, email, username, profilePictureAttachment, signUpDate "
                   + "FROM customers "
                   + "JOIN businesscustomer ON businesscustomer.customerId = customers.customerId "
                   + "WHERE businessId = (SELECT businessId FROM businesses WHERE username = ?);";
        List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList(sql, new Object[]{busnessUsername}));
        Customer[] customers = objectMapper.readValue(objectMapper.writeValueAsString(obj.get(0)), Customer[].class);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(customers));
    }

    public ResponseEntity<Object> getBusinessProfile(String business, Boolean isUserProfile) throws JsonGenerationException, JsonMappingException, IOException{
        ArrayList<String> errors = new ArrayList<String>();
        ObjectMapper objectMapper = new ObjectMapper();
        GenericReturn gr = new GenericReturn();
        try {
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT * FROM businesses WHERE username = ?", new Object[]{business}));
            Business businessObj = objectMapper.readValue(objectMapper.writeValueAsString(((ArrayList<Object>) obj.get(0)).get(0)), Business.class);
            businessObj.setIsUserProfile(isUserProfile);
            businessObj.setPassword(null);
            businessObj.setAddressObj(addressService.getAddressById(businessObj.getAddress()));
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(businessObj));
        } catch (Exception e) {
            errors.add("An Unexpected Error Occured");
            gr.setErrors(errors);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(gr));
        }
    }

}

package com.fpo.app.api.service;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpo.app.api.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Address getAddressById(String addressId) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<Object> obj = Collections.singletonList(jdbcTemplate.queryForList("SELECT * FROM addresses WHERE addressId = ?", new Object[]{addressId}));
            Address address = objectMapper.readValue(objectMapper.writeValueAsString(((ArrayList<Object>) obj.get(0)).get(0)), Address.class);
            return address;   
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

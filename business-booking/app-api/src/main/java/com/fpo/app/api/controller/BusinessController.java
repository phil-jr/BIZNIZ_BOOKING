package com.fpo.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpo.app.api.model.returnModel.GenericReturn;
import com.fpo.app.api.service.BusinessService;
import com.fpo.app.api.util.ExistsValidateGet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BusinessController extends BaseController {

    @Autowired
    BusinessService businessService;

    @Autowired
    ExistsValidateGet existsValidate;

    @PostMapping("/business/signup")
    public ResponseEntity<Object> userSignUp(@RequestBody String payload) throws IOException {
        return businessService.businessSignUp(payload);
    }

    @PostMapping("/business/signin")
    public ResponseEntity<Object> userSignIn(@RequestBody String payload) throws IOException {
        return businessService.businessSignIn(payload);
    }

    @GetMapping("/business/search/{search}")
    public ResponseEntity<Object> businessSearch(@PathVariable String search) throws IOException {
        return businessService.businessSearch(search);
    }

    @PostMapping("/business/signout")
    public ResponseEntity<Object> businessSignOut(@RequestBody String payload) throws IOException {
        return businessService.businessSignOut(payload);
    }

    @GetMapping("/business/customers/{business}")
    public ResponseEntity<Object> getBusinessCustomers(@PathVariable String business) throws IOException {
        return businessService.getBusinessCustomers(business);
    }

    @GetMapping("/business/is-following/{business}")
    public ResponseEntity<Object> getIsCustomerFollowing(@PathVariable String business, HttpServletRequest request) throws IOException {
        String customerSessionId = request.getHeader("customer-session");
        if(customerSessionId != null){
            return businessService.getIsCustomerFollowing(business, customerSessionId);
        } else {
            //Change generic return in future, maybe add more constructors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericReturn());
        }
    }

    @GetMapping("/business/profile")
    public ResponseEntity<Object> getBusinessProfileBySessionId(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String sessionId = request.getHeader("business-session");
        if(sessionId != null){
            String businessStr = objectMapper.writeValueAsString(existsValidate.getBusinessBySession(sessionId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(businessStr);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericReturn());
        }
    }

    @GetMapping("/business/profile/{business}")
    public ResponseEntity<Object> getBusinessProfile(@PathVariable String business, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("business-session");
        if(sessionId != null){
            if(existsValidate.isBusinessUser(business, sessionId)){
                return businessService.getBusinessProfile(business, true);
            } else {
                return businessService.getBusinessProfile(business, false);
            }
        } else {
            return businessService.getBusinessProfile(business, false);
        }
    }

}

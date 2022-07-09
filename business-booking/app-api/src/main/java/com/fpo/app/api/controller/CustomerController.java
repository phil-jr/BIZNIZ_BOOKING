package com.fpo.app.api.controller;

import com.fpo.app.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer/signup")
    public ResponseEntity<Object> userSignUp(@RequestBody String payload) throws IOException {
        return customerService.customerSignUp(payload);
    }

    @PostMapping("/customer/signin")
    public ResponseEntity<Object> userSignIn(@RequestBody String payload) throws IOException {
        return customerService.customerSignIn(payload);
    }

    @GetMapping("/customer/search/{search}")
    public ResponseEntity<Object> businessSearch(@PathVariable String search) throws IOException {
        return customerService.customerSearch(search);
    }

    @PostMapping("/customer/businessFollow") //TODO secure this endpoint
    public ResponseEntity<Object> customerBusinessFollow(@RequestBody String payload, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("customer-session");
        return customerService.customerBusinessFollow(payload, sessionId);
    }

    @PostMapping("/customer/businessUnfollow") //TODO secure this endpoint
    public ResponseEntity<Object> customerBusinessUnfollow(@RequestBody String payload, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("customer-session");
        return customerService.customerBusinessUnfollow(payload, sessionId);
    }

    @PostMapping("/customer/signout") //TODO secure this endpoint
    public ResponseEntity<Object> businessSignOut(@RequestBody String payload) throws IOException {
        return customerService.customerSignOut(payload);
    }

    @GetMapping("/customer/profile/{customer}")
    public ResponseEntity<Object> getCustomerProfile(@PathVariable String customer) throws IOException {
        return customerService.getCustomerProfile(customer);
    }

}

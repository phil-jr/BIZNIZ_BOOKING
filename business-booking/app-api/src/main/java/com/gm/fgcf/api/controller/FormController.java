package com.gm.fgcf.api.controller;

import com.gm.fgcf.api.service.FormService;
import com.gm.fgcf.api.util.ExistsValidateGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FormController extends BaseController {

    @Autowired
    FormService formService;

    @Autowired
    ExistsValidateGet existsValidate;

    @GetMapping("/forms/formData/{formId}")//SECURE THIS!
    public ResponseEntity<Object> getFormData(@PathVariable String formId, HttpServletRequest request) throws Exception {
        String sessionId = request.getHeader("business-session");
        if (existsValidate.doesOwnForm(formId, sessionId)) {
            return formService.getFormData(formId, sessionId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"Message\":\"Oops\", \"Errors\":\"An unexpected error occurred\"}");
        }
    }

    @GetMapping("/forms/formMetadata/{business}/{formLinkName}")
    public ResponseEntity<Object> getFormMetadataByLinkName(@PathVariable String formLinkName, @PathVariable String business, HttpServletRequest request) throws Exception {
        return formService.getFormMetadataByLinkName(formLinkName, business);
    }

    @GetMapping("/forms/forms")
    public ResponseEntity<Object> getForms(HttpServletRequest request) throws Exception {
        String sessionId = request.getHeader("business-session");
        return formService.getForms(sessionId);
    }

    @PostMapping("/forms/create") //TODO secure this endpoint
    public ResponseEntity<Object> createForm(@RequestBody String payload, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("business-session");
        return formService.createForm(payload, sessionId);
    }

    @PostMapping("/forms/bookAppointment") //TODO secure this endpoint
    public ResponseEntity<Object> bookAppointment(@RequestBody String payload, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("customer-session");
        return formService.bookAppointment(payload, sessionId);
    }

    @GetMapping("/forms/getAppointmentTimes/{businessUsername}/{formLinkName}") //TODO secure this endpoint
    public ResponseEntity<Object> getAppointmentTimes(@PathVariable String businessUsername, @PathVariable String formLinkName, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("business-session");
        if(sessionId != null){
            if (existsValidate.doesOwnFormByLinkName(formLinkName, sessionId)) {
                return null; //formService.getAppointmentTimesBus(businessUsername, formLinkName);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"Message\":\"Oops\", \"Errors\":\"An unexpected error occurred\"}");
            }
        }  else {
            return formService.getAppointmentTimesCust(businessUsername, formLinkName);
        }
    }
}

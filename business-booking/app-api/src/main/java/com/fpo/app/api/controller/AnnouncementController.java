package com.fpo.app.api.controller;

import org.springframework.web.bind.annotation.*;

import com.fpo.app.api.service.AnnouncementService;
import com.fpo.app.api.util.ExistsValidateGet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class AnnouncementController extends BaseController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    ExistsValidateGet existsValidate;

    @PostMapping("/announcement/post")
    public ResponseEntity<Object> postAnnouncement(@RequestBody String payload, HttpServletRequest request) throws IOException {
        String sessionId = request.getHeader("business-session");
        System.out.println(payload);
        return announcementService.postAnnouncement(sessionId, payload);
    } 

    
}
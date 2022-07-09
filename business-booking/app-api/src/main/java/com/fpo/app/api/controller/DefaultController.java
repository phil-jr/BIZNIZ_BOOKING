package com.fpo.app.api.controller;

import com.fpo.app.api.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    TestService testService;

    @GetMapping("/api/v1/hello/{page}/{perPage}")
    public String hello(@PathVariable String page, @PathVariable String perPage) throws Exception {
        return testService.testMethod(page, perPage);
    }

    @GetMapping("/api/v1/hashPassword/{password}/{password2}")
    public String passwordHash(@PathVariable String password, @PathVariable String password2) throws Exception {
        return testService.hashedPassword(password, password2);
    }

    @GetMapping("/api/v1/simplePassCrack")
    public String crackPass() throws Exception {
        return testService.crackPass("$2y$12$4hPEeQPt3UflTSLJhl6LvOVRTpMrVtq28sy1UV/fmkiluFeatIVYa");
    }
}

package com.gm.fgcf.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RedirectController {

    @RequestMapping(value = "/**/{path:[^.]*}")
    public String redirect() throws Exception {
        // Forward to home page so that route is preserved.
        return "forward:/";
    }

}

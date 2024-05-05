package com.example.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping(value = "/main-page")
    public String mainPage() {
        return "main-page";
    }

    @RequestMapping(value = "/")
    public String mainPageAlternative() {
        return "main-page";
    }
}

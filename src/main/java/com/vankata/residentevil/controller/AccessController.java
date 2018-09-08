package com.vankata.residentevil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController {

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized(ModelAndView mav) {
        mav.setViewName("unauthorized");
        return mav;
    }
}

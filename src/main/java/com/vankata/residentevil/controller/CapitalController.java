package com.vankata.residentevil.controller;

import com.vankata.residentevil.service.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CapitalController {

    private final CapitalService capitalService;

    @Autowired
    public CapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }
}

package com.vankata.residentevil.controller;

import com.vankata.residentevil.service.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    private final VirusService virusService;

    @Autowired
    public MapController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping("/map")
    @PreAuthorize("isAuthenticated()")
    public String getMapPage(Model model) {
        String geoJson = this.virusService.findAllMapViruses();
        model.addAttribute("geoJson", geoJson);
        return "map";
    }
}

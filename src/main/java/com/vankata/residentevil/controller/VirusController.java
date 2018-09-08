package com.vankata.residentevil.controller;

import com.vankata.residentevil.domain.model.service.CapitalServiceModel;
import com.vankata.residentevil.domain.model.view.CapitalViewModelPlusSelected;
import com.vankata.residentevil.domain.model.binding.VirusCreateBindingModel;
import com.vankata.residentevil.domain.model.binding.VirusEditBindingModel;
import com.vankata.residentevil.service.CapitalService;
import com.vankata.residentevil.service.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/viruses")
public class    VirusController {

    private final VirusService virusService;

    private final CapitalService capitalService;

    @Autowired
    public VirusController(VirusService virusService, CapitalService capitalService) {
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    @GetMapping("")
    public String showAll(Model model) {
        List<VirusCreateBindingModel> virusDtos = this.virusService.findAll();
        model.addAttribute("viruses", virusDtos);
        return "viruses";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public String viewAdd(HttpSession session, Model model) {
        model.addAttribute("virus", new VirusCreateBindingModel());
        List<CapitalServiceModel> capitalDtos = this.capitalService.findAllCapitals();
        session.setAttribute("capitals", capitalDtos);

        return "virus-form-page";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public String addVirus(@Valid @ModelAttribute(name = "virus") VirusCreateBindingModel virusDto,
                      BindingResult bindingResult,
                      @RequestParam(name = "capitals", required = false) int[] capitalIds,
                      HttpSession session,
                      RedirectAttributes redirectAttributes,
                      Model model) {

        boolean capitalsIsNull = capitalIds == null;

        if (capitalsIsNull) {
            model.addAttribute("noCapitals", true);
        }

        if (bindingResult.hasErrors() || capitalsIsNull) {
            return "virus-form-page";
        }

        this.virusService.addVirus(virusDto, capitalIds);
        session.removeAttribute("capitals");
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Virus '%s' was successfully added!", virusDto.getName()));

        return "redirect:/viruses";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public String viewEdit(@PathVariable(name = "id") long id, HttpSession session, Model model) {
        VirusEditBindingModel virusDtoWithCapitals = this.virusService.findById(id);
        List<CapitalViewModelPlusSelected> capitalDtos = this.capitalService
                .findAllCapitalsPlusSelected(virusDtoWithCapitals);
        model.addAttribute("virus", virusDtoWithCapitals);
        session.setAttribute("capitals", capitalDtos);

        return "virus-form-page";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public String editVirus(@Valid @ModelAttribute(name = "virus") VirusCreateBindingModel virusDto,
                            BindingResult bindingResult,
                            @RequestParam(name = "capitals", required = false) int[] capitalIds,
                            HttpSession session,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        boolean capitalsIsNull = capitalIds == null;

        if (capitalsIsNull) {
            model.addAttribute("noCapitals", true);
        }

        if (bindingResult.hasErrors() || capitalsIsNull) {
            return "virus-form-page";
        }

        this.virusService.editVirus(virusDto, capitalIds);
        session.removeAttribute("capitals");
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Virus '%s' was successfully edited!", virusDto.getName()));

        return "redirect:/viruses";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public String deleteVirus(@PathVariable(name = "id") long id, RedirectAttributes redirectAttributes) {
        String virusName = this.virusService.deleteVirus(id);
        redirectAttributes.addFlashAttribute("successMessage",
                String.format("Virus '%s' was successfully deleted!", virusName));

        return "redirect:/viruses";
    }
}

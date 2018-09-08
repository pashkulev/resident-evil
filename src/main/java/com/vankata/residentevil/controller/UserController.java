package com.vankata.residentevil.controller;

import com.vankata.residentevil.domain.model.binding.UserEditBindingModel;
import com.vankata.residentevil.domain.model.binding.UserLoginBindingModel;
import com.vankata.residentevil.domain.model.binding.UserRegisterBindingModel;
import com.vankata.residentevil.domain.model.service.UserServiceModel;
import com.vankata.residentevil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView viewLogin(@RequestParam(name = "logout", required = false) String logout,
                                  @RequestParam(name = "error", required = false) String error,
                                  ModelAndView mav) {
        if (logout != null) {
            mav.addObject("successMessage", "You have successfully logged out!");
        }

        if (error != null) {
            mav.addObject("errorMessage", "Invalid credentials!");
        }

        mav.addObject("user", new UserLoginBindingModel());
        mav.setViewName("user-form-page");
        return mav;
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute(name = "user") UserLoginBindingModel userLoginBindingModel,
//                        BindingResult bindingResult,
//                        HttpSession session,
//                        RedirectAttributes redirectAttributes,
//                        Model model) {
//        if (bindingResult.hasErrors()) {
//            return "user-form-page";
//        }
//
//        UserServiceModel userServiceModel = this.userService.findByUsername(userLoginBindingModel.getUsername());
//        if (userServiceModel == null) {
//            model.addAttribute("nonExistingUserMessage",
//                    String.format("User with username '%s' does not exist!", userLoginBindingModel.getUsername()));
//
//            return "user-form-page";
//        }
//
//        if (!userServiceModel.getPassword().equals(userLoginBindingModel.getPassword())) {
//            model.addAttribute("incorrectPasswordMessage",
//                    String.format("Incorrect password for user '%s'!", userLoginBindingModel.getUsername()));
//
//            return "user-form-page";
//        }
//
//        redirectAttributes.addFlashAttribute("successMessage",
//                String.format("Welcome %s! You have successfully logged in!", userLoginBindingModel.getUsername()));
//        session.setAttribute("username", userLoginBindingModel.getUsername());
//        session.setAttribute("isLoggedIn", true);
//
//        return "redirect:/";
//    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView viewRegister(ModelAndView mav) {
        mav.addObject("user", new UserRegisterBindingModel());
        mav.setViewName("user-form-page");
        return mav;
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(@Valid @ModelAttribute(name = "user") UserRegisterBindingModel userBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "user-form-page";
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(userBindingModel.getUsername());
        if (userServiceModel != null) {
            model.addAttribute("userExistsMessage",
                    String.format("User with username '%s' already exists! Try another one.", userBindingModel.getUsername()));

            return "user-form-page";
        }

        userServiceModel = this.userService.findByEmail(userBindingModel.getEmail());
        if (userServiceModel != null) {
            model.addAttribute("emailExistsMessage",
                    String.format("User with email '%s' already exists! Try another one.", userBindingModel.getEmail()));

            return "user-form-page";
        }

        this.userService.registerUser(userBindingModel);
        redirectAttributes.addFlashAttribute("successMessage", "Congratulations! You have successfully registered!");

        return "redirect:/users/login";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView allUsers(ModelAndView mav) {
        mav.addObject("users", this.userService.findAll());
        mav.setViewName("users");
        return mav;
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editUser(@PathVariable(name = "id") String userId, ModelAndView mav) {
        UserEditBindingModel userEditBindingModel = this.userService.findById(userId);
        mav.addObject("user", userEditBindingModel);
        mav.setViewName("user-edit-page");
        return mav;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView editUser(@Valid @ModelAttribute(name = "user") UserEditBindingModel bindingModel,
                                 BindingResult bindingResult,
                                 ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            mav.setViewName("user-edit-page");
            return mav;
        }

        this.userService.editUser(bindingModel);
        mav.setViewName("redirect:/users/all");
        return mav;
    }
}

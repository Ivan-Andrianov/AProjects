package org.guuproject.application.controllers;


import org.guuproject.application.models.User;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

    public UserService service;

    @Autowired
    public RegistrationController(UserService userService){
        this.service = userService;
    }

    @GetMapping("/registration")
    public String getRegistrationPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String executeRegistration(User user, Model model){
        if (!service.createUser(user)){
            model.addAttribute("error","true");
            return "registration";
        }
        service.createUser(user);

        return "redirect:/login";
    }
}

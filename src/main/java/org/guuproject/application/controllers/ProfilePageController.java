package org.guuproject.application.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpResponse;

@Controller
public class ProfilePageController {

    private UserService userService;
    private UserRepository userRepository;


    @Autowired
    public ProfilePageController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String getProfilePage(HttpServletResponse response){
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return String.format("redirect:/profile/%d",userId);
    }


    @GetMapping("/profile/{id}")
    public String getUserInformation(@PathVariable Long id, HttpServletResponse response){
        Long visitorId = userRepository.findUserByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).getId();

        if (visitorId.equals(visitorId)){
            response.addHeader("owner","true");
        }else response.addHeader("owner","false");

        return "profile";
    }
}

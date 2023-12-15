package org.guuproject.application.controllers;

import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SettingsController {

    private UserRepository userRepository;
    private UserService userService;

    public SettingsController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String getSettingsPage(){
        return "settings";
    }

    @PostMapping("/saveSettings/{name}/{lastname}/{age}/{country}")
    @ResponseBody
    public User saveSettings(@PathVariable String name, @PathVariable String lastname, @PathVariable Integer age, @PathVariable String country){
        User user = userRepository.findUserById(userService.getAuthenticatedUserId());
        user.setName(name);
        user.setLastname(lastname);
        user.setAge(age);
        user.setCountry(country);
        userRepository.save(user);
        return user;
    }

    @GetMapping("/getUserSettings")
    @ResponseBody
    public User getUserSettings(){
        return userRepository.findUserById(userService.getAuthenticatedUserId());
    }
}

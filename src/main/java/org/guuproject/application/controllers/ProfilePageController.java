package org.guuproject.application.controllers;


import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String getProfilePage(){
        return "profile";
    }


    //Создается JSON-представление объекта User
    @GetMapping("/profile/{id}")
    @ResponseBody
    public User getUserInformation(@PathVariable Long id){
        return userRepository.findUserById(id);
    }
}

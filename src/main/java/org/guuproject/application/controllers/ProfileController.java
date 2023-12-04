package org.guuproject.application.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.guuproject.application.models.Image;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;

@Controller
public class ProfileController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public ProfileController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getProfilePage(){
        Long id = userService.getAuthenticatedUserId();
        return String.format("redirect:/profile/%d",id);
    }


    @GetMapping("/profile/{id}")
    public String getProfilePage(@PathVariable Long id, HttpServletResponse response){
        Long visitorId = userService.getAuthenticatedUserId();

        if (visitorId.equals(visitorId)){
            response.addHeader("owner","true");
        }else response.addHeader("owner","false");

        return "profile";
    }

    @GetMapping("/getSortedListPhotos/{user_id}")
    @ResponseBody
    public List<Image> getSortedListPhotos(@PathVariable Long user_id){
        return userRepository.findUserById(user_id).getImages().stream().sorted(
                (x,y)-> -x.getDate_of_creating().compareTo(y.getDate_of_creating())
        ).toList();
    }
}

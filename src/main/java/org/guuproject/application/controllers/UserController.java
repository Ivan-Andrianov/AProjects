package org.guuproject.application.controllers;


import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    UserRepository userRepository;
    UserService userService;


    @Autowired
    public UserController(UserRepository userRepository,UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/addFriend")
    public ResponseEntity<?> addFriend(Long user_id, Long friend_id){
        if (userService.addFriend(userRepository.findUserById(user_id),userRepository.findUserById(friend_id))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(Long user_id, Long friend_id){
        if (userService.deleteFriend(userRepository.findUserById(user_id),userRepository.findUserById(friend_id))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/getUserFriends/{id}")
    public List<User> getUserFriends(@PathVariable(name = "id") Long id){
        return userRepository.findUserById(id).getFriends();
    }


}

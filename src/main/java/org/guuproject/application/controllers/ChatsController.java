package org.guuproject.application.controllers;


import org.guuproject.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class ChatsController {

    private UserRepository userRepository;

    @Autowired
    public ChatsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@PostMapping("/send/{id}")
    public ResponseEntity<?> sendMessage(@PathVariable(name = "id") Long userId,@PathVariable(name = "text") String message){

    }*/
}

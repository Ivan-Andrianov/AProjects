package org.guuproject.application.controllers;


import org.guuproject.application.models.User;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserRepository userRepository;


    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/addFriend")
    public ResponseEntity<?> addFriend(Long user_id, Long friend_id){
        User user = userRepository.findUserById(user_id);

        userRepository.flush();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

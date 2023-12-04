package org.guuproject.application.controllers;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.servlet.http.HttpServletResponse;
import org.guuproject.application.models.Chat;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.ChatRepository;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService;
    private ChatRepository chatRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, ChatRepository chatRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @ResponseBody
    @GetMapping("/getAuthenticatedUserId")
    public Long getAuthenticatedUserId(){
        return userService.getAuthenticatedUserId();
    }
    @DeleteMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(Long user_id, Long friend_id){
        if (userService.deleteFriend(userRepository.findUserById(user_id),userRepository.findUserById(friend_id))){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/getFriends/{id}")
    public List<User> getUserFriends(@PathVariable Long id){
        return userRepository.findUserById(id).getFriends();
    }

    //Для поиска юзеров по имени и фамилии
    @ResponseBody
    @GetMapping("/getSortedFriendsList")
    public List<User> getSortedUserFriends(@RequestParam Long id,@RequestParam String name){
        return userService.getSortedFriendsList(id,name);
    }
    @RequestMapping("/friends/{id}")
    public String getFriendsPage(@PathVariable(name="id") Long id){
        return "friends";
    }

    @ResponseBody
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable(name="id") Long id, HttpServletResponse response){
        if (userService.getAuthenticatedUserId()==id){
            response.addHeader("owner","true");
        }else{
            response.addHeader("owner","false");
            if (userService.areFriends(userService.getAuthenticatedUserId(), id,response)){
                response.addHeader("is_friends","true");
            }
            else response.addHeader("is_friends","false");
        }
        return userRepository.findUserById(id);
    }

    @ResponseBody
    @GetMapping("/getUserChats/{id}")
    public List<Chat> getUserChats(@PathVariable("id") Long user_id,HttpServletResponse response){
        return userRepository.findUserById(user_id).getChats();
    }

    @ResponseBody
    @PostMapping("/addFriend/{user_id}")
    public void addFriend(@PathVariable("user_id") Long user_id, HttpServletResponse response){
        User user_1 = userRepository.findUserById(userService.getAuthenticatedUserId());
        User user_2 = userRepository.findUserById(user_id);
        Chat chat = new Chat();
        chat.set_group(false);
        chat.setMembers(List.of(user_1,user_2));
        chatRepository.save(chat);
        jdbcTemplate.execute(String.format("insert into friendship values(%d,%d,%d)",user_1.getId(),user_2.getId(),chat.getId()));
        jdbcTemplate.execute(String.format("insert into friendship values(%d,%d,%d)",user_2.getId(),user_1.getId(),chat.getId()));
    }
}

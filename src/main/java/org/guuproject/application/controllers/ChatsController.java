package org.guuproject.application.controllers;


import org.guuproject.application.models.Chat;
import org.guuproject.application.models.Message;
import org.guuproject.application.models.User;
import org.guuproject.application.repositories.ChatRepository;
import org.guuproject.application.repositories.MessageRepository;
import org.guuproject.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatsController {

    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    @Autowired
    public ChatsController(UserRepository userRepository,ChatRepository chatRepository,MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @RequestMapping("/chat/{id}")
    public String getChatPage(@PathVariable("id") Long id){
        return "messagerPage";
    }

    @ResponseBody
    @GetMapping("/getChatInformation/{id}")
    public Chat getChatInformation(@PathVariable("id") Long friend_id){
        Long user_id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return chatRepository.findChatByUser1AndUser2(userRepository.findUserById(Math.max(user_id,friend_id)),userRepository.findUserById(Math.min(user_id,friend_id)));
    }

    @ResponseBody
    @RequestMapping("/sendMessage")
    public ResponseEntity<HttpStatus> sendMessage(@RequestParam Long chat_id, @RequestParam String text, @RequestParam Long sender_id){
        Chat chat = chatRepository.findChatById(chat_id);
        Message message = new Message();
        message.setChat_id(chat_id);
        message.setText(text);
        message.setSender(userRepository.findUserById(sender_id));
        messageRepository.save(message);
        return new ResponseEntity(HttpStatus.OK);
    }
}

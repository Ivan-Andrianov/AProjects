package org.guuproject.application.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.guuproject.application.models.Chat;
import org.guuproject.application.models.Message;
import org.guuproject.application.models.MessageInformation;
import org.guuproject.application.repositories.ChatRepository;
import org.guuproject.application.repositories.MessageRepository;
import org.guuproject.application.repositories.UserRepository;
import org.guuproject.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private ApplicationContext applicationContext;
    private UserService userService;

    @Autowired
    public ChatController(UserRepository userRepository,UserService userService, ApplicationContext applicationContext, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.applicationContext = applicationContext;
        this.userService = userService;
    }

    @RequestMapping("/chat/{id}")
    public String getChatPage(@PathVariable("id") Long id){
        return "chat";
    }

    @ResponseBody
    @GetMapping("/getChat/{id}")
    public Chat getChat(@PathVariable("id") Long id, HttpServletResponse response){
        response.addHeader("user_id",userService.getAuthenticatedUserId().toString());
        return chatRepository.findChatById(id);
    }

    @MessageMapping("/messages/{chat_id}")
    public Message handleMessage(@DestinationVariable Long chat_id, org.springframework.messaging.Message<MessageInformation> message){
        Message chat_message = new Message(chat_id,message.getPayload().getText(), userRepository.findUserById(message.getPayload().getSender_id()));
        messageRepository.save(chat_message);
        return chat_message;
    }

    @GetMapping("/messages/{user_id}")
    public String getMessagesPage(@PathVariable Long user_id){
        if (user_id != userService.getAuthenticatedUserId()) return "error";
        return "messages";
    }


}

package org.guuproject.application.services;

import org.guuproject.application.models.Chat;
import org.guuproject.application.models.User;
import org.guuproject.application.models.enums.Role;
import org.guuproject.application.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.guuproject.application.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    public PasswordEncoder passwordEncoder;
    public UserRepository userRepository;
    private ChatRepository chatRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ChatRepository chatRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public boolean createUser(User user){
        if (userRepository.findUserByEmail(user.getUsername())!=null) return false;
        else{
            user.setActive(true);
            user.setRole(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setFriendsId(new ArrayList<>());
            userRepository.save(user);
            return true;
        }
    }

    public boolean addFriend(User user,User friend){
        try {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            Chat chat = new Chat(user,friend);
            chatRepository.save(chat);
            userRepository.flush();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean deleteFriend(User user,User friend){
        try{
            user.getFriends().remove(friend);
            userRepository.flush();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

}

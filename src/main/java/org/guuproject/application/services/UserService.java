package org.guuproject.application.services;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import org.guuproject.application.models.Chat;
import org.guuproject.application.models.Image;
import org.guuproject.application.models.User;
import org.guuproject.application.models.enums.Role;
import org.guuproject.application.repositories.ChatRepository;
import org.guuproject.application.repositories.ImageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.guuproject.application.repositories.UserRepository;

import javax.sql.DataSource;
import java.util.*;


@Service
public class UserService {

    public PasswordEncoder passwordEncoder;
    public UserRepository userRepository;
    private ChatRepository chatRepository;
    public ImageRepository imageRepository;
    public ApplicationContext applicationContext;
    public DataSource dataSource;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ChatRepository chatRepository, ImageRepository imageRepository, ApplicationContext applicationContext, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.imageRepository = imageRepository;
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }

    public boolean createUser(User user){
        if (userRepository.findUserByUsername(user.getUsername())!=null) return false;
        else{
            user.setActive(true);
            user.setRole(Role.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAvatar(new Image("/pictures/avatars/0.jpg"));
            user.setFriendsId(new ArrayList<>());
            userRepository.save(user);
            return true;
        }
    }

    public boolean addFriend(User user, User friend, HttpServletResponse response){
        try {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            Chat chat = new Chat();
            chat.set_group(false);
            chatRepository.save(chat);
            response.addHeader("chat_id",chat.getId().toString());
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

    public List<User> getSortedFriendsList(Long id,String name){
        return userRepository.findUserById(id).getFriends().stream().filter(x->(x.getName()+x.getLastname()).toLowerCase().indexOf(name.toLowerCase())!=-1).toList();
    }

    public Long getAuthenticatedUserId(){
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public void attachAvatar(Long user_id,Long image_id){
        userRepository.findUserById(user_id).setAvatar(imageRepository.findImageById(image_id));
    }

    public boolean areFriends(Long user_id_1, Long user_id_2, HttpServletResponse response){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        List<Map<String,Object>> list = template.queryForList(String.format("select chat_id from friendship where user_id=%d and friend_id=%d",user_id_1,user_id_2));
        if (list.isEmpty()) return false;
        else{
            response.addHeader("chat_id",list.get(0).get("chat_id").toString());
            return true;
        }
    }
}

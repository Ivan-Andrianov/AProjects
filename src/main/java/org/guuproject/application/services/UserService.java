package org.guuproject.application.services;

import org.guuproject.application.models.User;
import org.guuproject.application.models.enums.Role;
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
    public UserRepository repository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public boolean createUser(User user){
        if (repository.findUserByEmail(user.getUsername())!=null) return false;
        else{
            user.setActive(true);
            user.setRoles(new HashSet<>(Set.of(Role.ROLE_USER)));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setFriendsId(new ArrayList<>());
            repository.save(user);
            return true;
        }
    }

    public boolean addFriend(User user,User friend){
        try {
            user.getParentFriends().add(friend);
            repository.flush();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean deleteFriend(User user,User friend){
        try{
            user.getParentFriends().remove(friend);
            repository.flush();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

}

package org.guuproject.application.services;


import org.guuproject.application.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.guuproject.application.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {


    public UserRepository repository;

    @Autowired
    public CustomUserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);

        return user==null?new User():user;
    }


}

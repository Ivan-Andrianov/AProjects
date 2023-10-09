package org.guuproject.application.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.guuproject.application.models.enums.Role;
import org.guuproject.application.models.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;
    private String username;
    private String phone_number;
    private String name;
    private String lastname;
    private boolean active;

    @Column(length = 1000)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime date_of_created;//necessary

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name="friendship",joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="friend_id"))
    private List<User> friends = new ArrayList<>();
    private String git;
    private Image avatar;
    private String country;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> authorities = new ArrayList<>();
        authorities.add(role);
        return  authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @PrePersist
    public void init(){
        date_of_created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getDateOfCreated() {
        return date_of_created;
    }

    public void setDateOfCreated(LocalDateTime date_of_created) {
        this.date_of_created = date_of_created;
    }


    public void setFriendsId(List<User> friends) {
        this.friends = friends;
    }
    public List<User> getFriends() {
        return friends;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String gitHub) {
        this.git = gitHub;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setFriends(List<User> parentFriends) {
        this.friends = parentFriends;
    }
}

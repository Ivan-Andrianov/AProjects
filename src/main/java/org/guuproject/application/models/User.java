package org.guuproject.application.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    private String name;//necessary

    private String lastname;//necessary

    private boolean active;
    

    @Column(length = 1000)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name="dateOfCreated")
    private LocalDateTime dateOfCreated;//necessary

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name="friendship",joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="friend_id"))
    private List<User> friends = new ArrayList<>();


    @Column(name="git")
    private String gitHub;


    @Column(name = "avatar")
    private String avatar;

    @Column(name="country")
    private String country;

    @Column(name="status")
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
        return email;
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
        dateOfCreated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }


    public void setFriendsId(List<User> friends) {
        this.friends = friends;
    }
    public List<User> getFriends() {
        return friends;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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

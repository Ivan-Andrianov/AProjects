package org.guuproject.application.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.guuproject.application.configuration.NewsSerializer;
import org.guuproject.application.models.enums.Role;
import org.guuproject.application.models.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private boolean active;
    private Integer age;
    @Column(length = 1000)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime date_of_created;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="friendship",joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="friend_id"))
    private List<User> friends = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="avatar_id")
    private Image avatar;
    private String country;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "chat_member",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_image", joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="image_id"))
    private List<Image> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_news",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="news_id"))
    @JsonSerialize(using = NewsSerializer.class)
    private List<News> news = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> authorities = new ArrayList<>();
        authorities.add(role);
        return authorities;
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

    public void setFriends(List<User> parentFriends) {
        this.friends = parentFriends;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}

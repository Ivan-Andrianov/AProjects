package org.guuproject.application.models;

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
    @Column(name = "id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="name")
    private String name;//necessary

    @Column(name="lastname")
    private String lastname;//necessary

    @Column(name="active")
    private boolean active;

    @Column(name = "password",length = 1000)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "roles")
    @CollectionTable(name = "roles",joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(Set.of(Role.ROLE_USER));

    @Column(name="dateOfCreated")
    private LocalDateTime dateOfCreated;//necessary



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="friendship",joinColumns = @JoinColumn(referencedColumnName = "id",name="user_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id",name="friend_id"))
    @Column(name = "friends")
    private List<User> friends = new ArrayList<>();//массив айди друзей.


    @Column(name="git")
    private String gitHub;

    @Column(name="avatar")
    private String address_of_avatar;

    @Column(name="country")
    private String country;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
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

    public void setId(Long id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getAddress_of_avatar() {
        return address_of_avatar;
    }

    public void setAddress_of_avatar(String address_of_avatar) {
        this.address_of_avatar = address_of_avatar;
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
}

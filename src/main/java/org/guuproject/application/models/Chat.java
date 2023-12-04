package org.guuproject.application.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_group")
    private boolean group;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chat_id")
    private List<Message> listOfMessages = new ArrayList<>();

    @OneToMany
    @JoinTable(
            name = "chat_member",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    @JsonManagedReference
    private List<User> members = new ArrayList<>();

    public Chat() {}

    public Chat(Long id, boolean group, List<Message> listOfMessages, List<User> members) {
        this.id = id;
        this.group = group;
        this.listOfMessages = listOfMessages;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public boolean is_group() {
        return group;
    }

    public void set_group(boolean group) {
        this.group = group;
    }

    public List<Message> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(List<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}

package org.guuproject.application.models;

import jakarta.persistence.*;
import org.guuproject.application.models.composite_primary_keys.MessageId;

@Entity
@Table(name = "messages")
@IdClass(MessageId.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Id
    private long chat_id;

    @Column(nullable = false)
    private String text;

    @OneToOne
    private User sender;

    public Message() {}

    public Message(long chat_id, String text, User sender) {
        this.chat_id = chat_id;
        this.text = text;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}

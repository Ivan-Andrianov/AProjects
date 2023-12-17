package org.guuproject.application.models;

import jakarta.persistence.*;

@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="sender_id")
    private User sender;

    @Column(nullable = false)
    private String message;

    public Comment() {}

    public Comment(User sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

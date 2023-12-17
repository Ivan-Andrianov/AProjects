package org.guuproject.application.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private LocalDateTime date_of_creating;

    @OneToOne
    @JoinColumn(name="writer_id")
    private User writer;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="news_comment",joinColumns = @JoinColumn(name="news_id"),inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Comment> comments;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_like",joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "user_id")
    private Set<Long> likes = new HashSet<>();

    public News(){}

    public News(String topic, LocalDateTime date_of_creating, User writer, Image image, List<Comment> comments, Set<Long> likes) {
        this.topic = topic;
        this.date_of_creating = date_of_creating;
        this.writer = writer;
        this.image = image;
        this.comments = comments;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){this.id = id;}

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Long> getLikes() {
        return likes;
    }

    public LocalDateTime getDate_of_creating() {
        return date_of_creating;
    }

    public void setDate_of_creating(LocalDateTime date_of_creating) {
        this.date_of_creating = date_of_creating;
    }
}

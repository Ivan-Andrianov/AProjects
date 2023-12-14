package org.guuproject.application.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private int likes_count;
    private int reposts_count;

    @OneToOne
    @JoinColumn(name="writer_id")
    private User writer;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="news_comment",joinColumns = @JoinColumn(name="news_id"),inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Comment> comments;

    @ElementCollection
    @CollectionTable(name = "news_like",joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "user_id")
    private Set<Long> likes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public void setLikes(Set<Long> likes) {
        this.likes = likes;
    }

}

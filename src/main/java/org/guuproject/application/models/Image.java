package org.guuproject.application.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="path")
    private String picture_path;

    @Column(name="date_of_creating")
    private LocalDateTime date_of_creating;

    public Image() {}

    public Image(String picture_path) {
        this.picture_path = picture_path;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return picture_path;
    }

    public void setPath(String picture_path) {
        this.picture_path = picture_path;
    }

    public LocalDateTime getDate_of_creating() {
        return date_of_creating;
    }

    public void setDate_of_creating(LocalDateTime date_of_creating) {
        this.date_of_creating = date_of_creating;
    }
}

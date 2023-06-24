package org.guuproject.application.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long project_id;

    @Column(name = "date_of_creating")
    private LocalDateTime dateOfCreating;

    @ElementCollection
    @CollectionTable()
    private List<User> members;


    private String gitShortcut;

    public Project() {
    }

}


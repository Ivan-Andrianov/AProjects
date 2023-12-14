package org.guuproject.application.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Like {

    private Long user_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}

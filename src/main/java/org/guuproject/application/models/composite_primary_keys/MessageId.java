package org.guuproject.application.models.composite_primary_keys;

import java.io.Serializable;

public class MessageId implements Serializable {

    private Long id;
    private Long chat_id;

    public MessageId() {}

    public MessageId(Long id) {
        this.id = id;
    }

    public MessageId(Long id, Long chat_id) {
        this.id = id;
        this.chat_id = chat_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    @Override
    public int hashCode() {
        final int prime = 10;
        return (int) (this.getChat_id()*prime+this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;

        if (this.getClass()!=obj.getClass()) return false;

        if (this==obj) return true;

        if (this.getId()==((MessageId) obj).getId() && this.getChat_id()==((MessageId) obj).getChat_id()) return true;

        return false;
    }
}

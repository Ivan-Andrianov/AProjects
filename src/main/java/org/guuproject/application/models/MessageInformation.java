package org.guuproject.application.models;

public class MessageInformation {
    private Long sender_id;
    private Long chat_id;
    private String text;

    public MessageInformation() {}

    public MessageInformation(Long user_id, Long chat_id, String text) {
        this.sender_id = user_id;
        this.chat_id = chat_id;
        this.text = text;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.mng.hermes.dto;

import com.mng.hermes.model.Message;

public class MessageDTO {

    private int id;
    private String content;
    private String name;

    public MessageDTO(int id, String content, String name) {
        this.id = id;
        this.content = content;
        this.name = name;
    }

    public static MessageDTO construct(Message message) {
        return new MessageDTO(message.getId(), message.getContent(), message.getOwner().getNickName());
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}

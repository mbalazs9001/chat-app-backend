package com.mng.hermes.model;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User owner;
    private String content;

    @Enumerated(value = EnumType.STRING)
    private TargetType type;

    private String targetAddress;

    public Message() {
    }

    public Message(User owner, String content, TargetType type, String targetAddress) {
        this.owner = owner;
        this.content = content;
        this.type = type;
        this.targetAddress = targetAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public TargetType getType() {
        return type;
    }

    public void setType(TargetType type) {
        this.type = type;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", owner=" + owner +
                ", type=" + type +
                ", targetAddress='" + targetAddress + '\'' +
                '}';
    }

}

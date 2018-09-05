package com.mng.hermes.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickName;

    private String introduction;
    private String givenName;
    private String familyName;
    private String picture;

    @Column(length = 1024)
    private String token;

    @ManyToMany
    private Set<Room> managedRooms = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Message> messages = new HashSet<>();

    public User() {
    }

    public User(String email, String nickName, String introduction, String givenName, String familyName, String picture, Set<Room> managedRooms, Set<Message> messages) {
        this.email = email;
        this.nickName = nickName;
        this.introduction = introduction;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.managedRooms = managedRooms;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<Room> getManagedRooms() {
        return managedRooms;
    }

    public void setManagedRooms(Set<Room> managedRooms) {
        this.managedRooms = managedRooms;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", picture='" + picture + '\'' +
                ", token=" + token +
                '}';
    }
}
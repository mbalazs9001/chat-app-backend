package com.mng.hermes.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToMany
    private Set<User> admins;

    public String getName() {
        return name;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admins=" + admins +
                '}';
    }

    public Room() {
    }

    public Room(String name, Set<User> admins) {
        this.name = name;
        this.admins = admins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }
}

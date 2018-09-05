package com.mng.hermes.service;

import com.google.gson.*;
import com.mng.hermes.model.User;
import com.mng.hermes.repository.UserRepository;
import com.mng.hermes.util.HttpRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private HttpRequest http;
    private Gson gson;

    public UserService(UserRepository userRepository, HttpRequest http, Gson gson) {
        this.userRepository = userRepository;
        this.http = http;
        this.gson = gson;
    }

    public User getUser(String authHeader) throws IllegalAccessException {
        String token = processHeader(authHeader);
        User user = userRepository.getUserByToken(token);
        if (user == null) {
            user = fetchUser(token);
        }
        return user;
    }

    private String processHeader(String authHeader) throws IllegalAccessException {
        String[] headerParts = authHeader.split(" ");
        if (headerParts.length != 2) {
            throw new IllegalAccessException("Authorization header format is invalid.");
        }
        return headerParts[1];
    }

    private User fetchUser(String token) throws IllegalAccessException {
        User user = http.fetchUserData(token);
        if (user == null) {
            throw new IllegalAccessException("Invalid token!");
        }
        //User user = gson.fromJson(data, User.class);
        User userInDb = userRepository.getUserByEmail(user.getEmail());
        if (userInDb == null) {
            user.setToken(token);
            userInDb = userRepository.save(user);
        } else {
            userInDb.setToken(token);
            userRepository.flush();
        }
        return userInDb;
    }

    public void logoutUser(String authHeader) {
        User currentUser = null;
        try {
            currentUser = getUser(authHeader);
            currentUser.setToken(null);
            userRepository.save(currentUser);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(User user, User currentUser) {
        currentUser.setNickName(user.getNickName());
        currentUser.setIntroduction(user.getIntroduction());
        userRepository.save(currentUser);
    }
}

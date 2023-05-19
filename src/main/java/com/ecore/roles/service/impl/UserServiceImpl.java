package com.ecore.roles.service.impl;

import com.ecore.roles.client.UserClient;
import com.ecore.roles.model.User;
import com.ecore.roles.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    public UserServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    public User getUser(UUID id) {
        return userClient.getUser(id).getBody();
    }

    public List<User> getUsers() {
        return userClient.getUsers().getBody();
    }
}

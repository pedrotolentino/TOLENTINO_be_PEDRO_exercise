package com.ecore.roles.service;

import com.ecore.roles.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUser(UUID id);

    List<User> getUsers();
}

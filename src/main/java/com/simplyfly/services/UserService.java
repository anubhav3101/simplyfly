package com.simplyfly.services;

import com.simplyfly.model.user;

import java.util.Optional;

public interface UserService {
    user registerUser(user user);
    Optional<user> findByUsername(String username);
}

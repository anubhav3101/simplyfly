package com.simplyfly.services;

import com.simplyfly.model.user;

import com.simplyfly.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public user registerUser(user user) {

        if (userRepository.existsByEmail(user.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<user> findByUsername(String username) {
        return userRepository.findByName(username);
    }
}

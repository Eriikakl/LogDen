package com.logden.backend.service;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.User;
import com.logden.backend.domain.UserRepository;

@Service
public class UserService {

     private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

package com.logden.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.logden.backend.domain.User;
import com.logden.backend.domain.UserRepository;
import com.logden.backend.dto.LoginRequest;
import com.logden.backend.dto.RegisterRequest;
import com.logden.backend.dto.UpdateUserRequest;
import com.logden.backend.dto.UpdateUserRoleRequest;
import com.logden.backend.exception.ResourceAlreadyExistsException;
import com.logden.backend.exception.ResourceNotFoundException;
import com.logden.backend.security.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CurrentUserService currentUserService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.currentUserService = currentUserService;
    }

    // Get a user by its ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // Update a user's role as an admin
    public User updateUserRole(Long id, UpdateUserRoleRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!request.getRole().equals("USER") &&
                !request.getRole().equals("ADMIN")) {
            throw new IllegalArgumentException("Invalid role");
        }

        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    // Update the current user's information
    public User updateCurrentUser(UpdateUserRequest request) {

        User user = currentUserService.getCurrentUser();

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()
                && !existingUser.get().getUserId().equals(user.getUserId())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    // Delete a user as an admin
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    // Get all users as an admin
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Register a new user
    public User registerUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    // Log in the user
    public String loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtService.generateToken(user);
    }
}

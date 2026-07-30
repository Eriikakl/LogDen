package com.logden.backend.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logden.backend.domain.User;
import com.logden.backend.dto.RegisterRequest;
import com.logden.backend.dto.UserDto;
import com.logden.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return UserDto.fromEntity(user);
    }

}

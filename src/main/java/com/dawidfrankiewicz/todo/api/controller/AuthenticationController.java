package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        validateUser(user);

        try {
            userRepository.saveAndFlush(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

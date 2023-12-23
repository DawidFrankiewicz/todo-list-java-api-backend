package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserRepository userRepository;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid User user) {
        try {
            userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // TODO: REMOVE THIS ENDPOINT FOR PRODUCTION // ONLY FOR TESTING PURPOSES
    @GetMapping("/users")
    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

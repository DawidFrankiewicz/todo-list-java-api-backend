package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    private void validateUser(User user) {
        if (user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {

        // Check if user with this email already exists
        if(authenticationService.getUser(user.getUserName()).getUserName() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        validateUser(user);

        authenticationService.registerUser(user);
    }

    @GetMapping("/login")
    public void loginUser(@RequestBody User user) {
        // TaskService.setUserId(authenticationService.loginUser(user));
    }
}

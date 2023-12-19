package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationService authenticationService;

    public int getAuthorizedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User authUser = authenticationService.getUserByName(currentPrincipalName);

        if (authUser.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        }

        return authUser.getId();
    }
}

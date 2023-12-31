package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Status;
import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.repository.StatusRepository;
import com.dawidfrankiewicz.todo.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/config")
@RequiredArgsConstructor
public class UserConfigController {
    private final StatusRepository statusRepository;
    private final SecurityService securityService;

    @GetMapping("/status")
    public List<Status> getStatuses() {
        int userId = securityService.getAuthorizedUserId();
        return statusRepository.findAllByUser_id(userId);
    }

    @PostMapping("/status")
    public void addStatus(@RequestBody @Valid Status status) {
        User user = securityService.getAuthorizedUser();
        status.setUser(user);

        try{
            statusRepository.saveAndFlush(status);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping("/status/{id}")
    public void deleteTask(@PathVariable int id) {
        int userId = securityService.getAuthorizedUserId();
        statusRepository.deleteByUser_idAndId(userId, id);
    }

    @Transactional
    @PutMapping("/status/{id}")
    public void editStatus(@PathVariable int id, @Valid @RequestBody Status status) {
        int userId = securityService.getAuthorizedUserId();
        Status receviedstatus = statusRepository.findByUser_idAndId(userId, id);

        if (receviedstatus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status was not found");
        }

        if (status.getStatus() != null) receviedstatus.setStatus(status.getStatus());
    }
}



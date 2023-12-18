package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Status;
import com.dawidfrankiewicz.todo.service.SecurityService;
import com.dawidfrankiewicz.todo.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/config")
public class UserConfigController {
    @Autowired
    UserConfigService userConfigService;
    @Autowired
    private SecurityService securityService;

    private void validateStatus(Status status) {
        if (status.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid: {status}");
        }
    }

    @GetMapping("/status")
    public List<Status> getStatuses() {
        return userConfigService.getStatuses(securityService.getAuthorizedUserId());
    }

    @PostMapping("status")
    public void addStatus(@RequestBody Status status) {
        validateStatus(status);
        int userId = securityService.getAuthorizedUserId();

        userConfigService.addStatus(userId, status);
    }

    @DeleteMapping("status/{id}")
    public void deleteTask(@PathVariable int id) {
        int userId = securityService.getAuthorizedUserId();
        userConfigService.deleteStatus(userId, id);
    }

    @PutMapping("status/{id}")
    public void editStatus(@PathVariable int id, @RequestBody Status status) {
        int userId = securityService.getAuthorizedUserId();
        validateStatus(status);

        userConfigService.editStatus(userId, id, status);
    }
}



package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.service.TaskService;
import com.dawidfrankiewicz.todo.service.AuthenticationService;

import com.dawidfrankiewicz.todo.api.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private AuthenticationService authenticationService;

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid: {title, description, status}");
        }
    }

    private int getAuthorizedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User authUser = authenticationService.getUserByName(currentPrincipalName);

        if (authUser.getId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        }

        return authUser.getId();
    }

    @GetMapping()
    public List<Task> getTasks() {
        int userId = getAuthorizedUserId();

        return taskService.getTasks(userId);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) throws ResponseStatusException {
        int userId = getAuthorizedUserId();

        Task recivedTask = taskService.getTask(userId, id);

        if (recivedTask.getId() == null && recivedTask.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }

        return recivedTask;
    }

    @PostMapping()
    public void addTask(@RequestBody Task task) {
        int userId = getAuthorizedUserId();
        validateTask(task);

        taskService.addTask(userId, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        int userId = getAuthorizedUserId();
        taskService.deleteTask(userId, id);
    }

    @PutMapping("/{id}")
    public void editTask(@PathVariable int id, @RequestBody Task task) {
        int userId = getAuthorizedUserId();
        validateTask(task);

        taskService.editTask(userId, id, task);
    }
}

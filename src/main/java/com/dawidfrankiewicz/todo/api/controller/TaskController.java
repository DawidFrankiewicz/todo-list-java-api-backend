package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.service.SecurityService;
import com.dawidfrankiewicz.todo.service.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final SecurityService securityService;

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid: {title, description, status}");
        }
    }


    @GetMapping()
    public List<Task> getTasks() {
        int userId = securityService.getAuthorizedUserId();

        return taskService.getTasks(userId);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) throws ResponseStatusException {
        int userId = securityService.getAuthorizedUserId();

        Task recivedTask = taskService.getTask(userId, id);

        if (recivedTask.getId() == null && recivedTask.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }

        return recivedTask;
    }

    @PostMapping()
    public void addTask(@RequestBody Task task) {
        int userId = securityService.getAuthorizedUserId();
        validateTask(task);

        taskService.addTask(userId, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        int userId = securityService.getAuthorizedUserId();
        taskService.deleteTask(userId, id);
    }

    @PutMapping("/{id}")
    public void editTask(@PathVariable int id, @RequestBody Task task) {
        int userId = securityService.getAuthorizedUserId();
        validateTask(task);

        taskService.editTask(userId, id, task);
    }
}

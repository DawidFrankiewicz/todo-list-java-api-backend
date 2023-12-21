package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.repository.TaskRepository;
import com.dawidfrankiewicz.todo.service.SecurityService;

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
    private final TaskRepository taskRepository;
    private final SecurityService securityService;

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid: {title, description, status}");
        }
    }


    @GetMapping()
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) throws ResponseStatusException {
        int userId = securityService.getAuthorizedUserId();

        Task recivedTask = taskRepository.findByIdForUser(userId, id);

        if (recivedTask.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }

        return recivedTask;
    }

    @PostMapping()
    public void addTask(@RequestBody Task task) {
        int userId = securityService.getAuthorizedUserId();
        validateTask(task);
        task.setUserId(userId);

        taskRepository.saveAndFlush(task);
    }

    // TODO: UPDATE THIS ENDPOINT
    // @DeleteMapping("/{id}")
    // public void deleteTask(@PathVariable int id) {
    //     int userId = securityService.getAuthorizedUserId();
    //     taskService.deleteTask(userId, id);
    // }

    // TODO: UPDATE THIS ENDPOINT
    // @PutMapping("/{id}")
    // public void editTask(@PathVariable int id, @RequestBody Task task) {
    //     int userId = securityService.getAuthorizedUserId();
    //     validateTask(task);

    //     taskService.editTask(userId, id, task);
    // }
}

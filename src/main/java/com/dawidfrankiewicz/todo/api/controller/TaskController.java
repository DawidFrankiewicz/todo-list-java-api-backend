package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.List;

@RestController
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }

    @GetMapping("/task")
    public Task getTask(@RequestParam int id) {
        Optional<Task> task = taskService.getTask(id);
        return task.orElse(null);
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    // @PostMapping("/task")
    // public Task addTask(@RequestBody String title, String description) {
    //     return taskService.addTask(title, description).orElse(null);
    // }

    // @DeleteMapping("/task")
    // public Task deleteTask(@RequestParam UUID id) {
    //     taskService.deleteTask(id);
    //     Optional<Task> task = taskService.deleteTask(id);
    //     return task.orElse(null);
    // }

    // @PutMapping("/task")
    // public Task editTask(
    //         @RequestBody String title, String description,
    //         @RequestParam UUID id) {
    //     return taskService.editTask(id, title, description).orElse(null);
    // }
}

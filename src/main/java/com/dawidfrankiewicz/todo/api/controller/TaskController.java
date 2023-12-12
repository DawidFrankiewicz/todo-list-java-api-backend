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

    @PostMapping("/task")
    public void addTask(@RequestParam String title, String description) {
        taskService.addTask(title, description);
    }

    @DeleteMapping("/task")
    public void deleteTask(@RequestParam int id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/task")
    public void editTask(@RequestParam int id, String title, String description) {
        taskService.editTask(id, title, description);
    }
}

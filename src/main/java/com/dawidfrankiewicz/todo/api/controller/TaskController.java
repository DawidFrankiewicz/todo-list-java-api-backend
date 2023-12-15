package com.dawidfrankiewicz.todo.api.controller;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title or description cannot be null");
        }
    }

    @GetMapping()
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) {
        Task recivedTask = taskService.getTask(id);

        if (recivedTask.getId() == 0 && recivedTask.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id: " + id + " not found");
        }

        return recivedTask;
    }

    @PostMapping()
    public void addTask(@RequestBody Task task) {
        validateTask(task);
        taskService.addTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public void editTask(@PathVariable int id, @RequestBody Task task) {
        validateTask(task);
        taskService.editTask(id, task);
    }
}

package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.Task;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TaskService {
    private List<Task> taskList;

    public TaskService() {
        this.taskList = new ArrayList<Task>();
        Task task1 = new Task("Test title 1", "test description 1");
        Task task2 = new Task("Test title 2", "test description 2");
        Task task3 = new Task("Test title 3", "test description 3");
        Task task4 = new Task("Test title 4", "test description 4");
        Task task5 = new Task("Test title 5", "test description 5");

        taskList.addAll(Arrays.asList(task1, task2, task3, task4, task5));

    }

    public Optional<Task> getTask(UUID id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public Optional<Task> addTask(String title, String description) {
        if (!title.isEmpty() && !description.isEmpty()) {
            Task addedTask = new Task(title, description);
            taskList.add(addedTask);
            return Optional.of(addedTask);
        }
        return Optional.empty();
    }

    public Optional<Task> deleteTask(UUID id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                taskList.remove(task);
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public Optional<Task> editTask(UUID id, String title, String description) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                task.setDescription(description);
                task.setTitle(title);
            }
        }
        return Optional.empty();
    }
}
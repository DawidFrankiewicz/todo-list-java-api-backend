package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.database.dbConnect;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TaskService {
    private List<Task> taskList;

    public TaskService() {
        dbConnect db = new dbConnect();
        taskList = db.getTasks();
    }

    public Optional<Task> getTask(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    // public Optional<Task> addTask(String title, String description) {
    //     if (!title.isEmpty() && !description.isEmpty()) {
    //         Task addedTask = new Task(title, description);
    //         taskList.add(addedTask);
    //         return Optional.of(addedTask);
    //     }
    //     return Optional.empty();
    // }

    // public Optional<Task> deleteTask(UUID id) {
    //     for (Task task : taskList) {
    //         if (task.getId().equals(id)) {
    //             taskList.remove(task);
    //             return Optional.of(task);
    //         }
    //     }
    //     return Optional.empty();
    // }

    // public Optional<Task> editTask(UUID id, String title, String description) {
    //     for (Task task : taskList) {
    //         if (task.getId().equals(id)) {
    //             task.setDescription(description);
    //             task.setTitle(title);
    //         }
    //     }
    //     return Optional.empty();
    // }
}
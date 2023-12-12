package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.Task;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private List<Task> taskList;

    public TaskService() {
        DatabaseService db = new DatabaseService();
        taskList = db.getTasks(1);
        db.closeConnection();
    }

    public void updateTasks(int userId) {
        DatabaseService db = new DatabaseService();
        taskList = db.getTasks(userId);
        db.closeConnection();
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public Optional<Task> getTask(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }


    public void addTask(String title, String description) {
        if (!title.isEmpty() && !description.isEmpty()) {
            DatabaseService db = new DatabaseService();
            db.addTask(1 ,title, description);
            db.closeConnection();
            updateTasks(1);
        }
    }

    public void deleteTask(int id) {
        DatabaseService db = new DatabaseService();
        db.deleteTask(1, id);
        db.closeConnection();
        updateTasks(1);
    }

    public void editTask(int id, String title, String description) {
        DatabaseService db = new DatabaseService();
        db.editTask(1, id, title, description);
        db.closeConnection();
        updateTasks(1);
    }
}
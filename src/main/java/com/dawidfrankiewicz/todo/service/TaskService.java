package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.database.DatabaseConnection;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

@Service
public class TaskService {
    private Connection connection;

    public TaskService() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Task> getTasks(int userId) {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE user_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Task newTask = new Task();
                newTask.setId(resultSet.getInt("id"));
                newTask.setTitle(resultSet.getString("title"));
                newTask.setDescription(resultSet.getString("description"));
                newTask.setIsDone(resultSet.getBoolean("isDone"));

                taskList.add(newTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public Task getTask(int userId, int id) {
        Task task = new Task();
        String query = "SELECT * FROM tasks WHERE user_id = ? AND id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setIsDone(resultSet.getBoolean("isDone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }

    public void addTask(int userId, Task task) {
        String query = "INSERT INTO tasks (user_id, title, description, isDone) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsDone());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int userId, int id) {
        String query = "DELETE FROM tasks WHERE user_id = ? AND id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTask(int userId, int id, Task task) {
        String query = "UPDATE tasks SET title = ?, description = ?, isDone = ? WHERE user_id = ? AND id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.getIsDone());
            statement.setInt(4, userId);
            statement.setInt(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.dawidfrankiewicz.todo.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawidfrankiewicz.todo.api.model.Task;
import com.dawidfrankiewicz.todo.database.dbConnect;

public class DatabaseService {
    private dbConnect dbConnect;
    private Connection connection;

    @Autowired
    public DatabaseService() {
        this.dbConnect = new dbConnect();
        this.connection = dbConnect.getConnection();
    }

    public void closeConnection() {
        dbConnect.closeConnection();
    }

    public List<Task> getTasks(int userId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks WHERE user_id = " + userId);

            List<Task> tasks = new ArrayList<Task>();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("isDone"));
                tasks.add(task);
            }
            return tasks;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task getTask(int userId, int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM tasks WHERE user_id = " + userId + " AND id = " + id);

            if (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("isDone"));
                return task;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTask(int userId, String title, String description) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO tasks (user_id, title, description, isDone) VALUES (" + userId + ", '"
                    + title + "', '" + description + "', false)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int userId, int id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM tasks WHERE user_id = " + userId + " AND id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTask(int userId, int id, String title, String description) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE tasks SET title = '" + title + "', description = '" + description
                    + "' WHERE user_id = " + userId + " AND id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

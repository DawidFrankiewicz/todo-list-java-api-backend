package com.dawidfrankiewicz.todo.database;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.dawidfrankiewicz.todo.api.model.Task;

public class dbConnect {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/todo_list_api";
    private static final String dbUser = "root";
    private static final String dbPassword = "";
    private static Connection connection;

    public dbConnect() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

            List<Task> tasks = new ArrayList<Task>();
            while (resultSet.next()) {
                Task task = new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("isDone")
                );
                tasks.add(task);
            }
            return tasks;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

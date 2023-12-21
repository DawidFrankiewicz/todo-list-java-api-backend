package com.dawidfrankiewicz.todo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Remove after JPA repositories implementation
public class DatabaseConnection {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/todo_list_api";
    private static final String dbUser = "root";
    private static final String dbPassword = "";
    private static Connection connection;

    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            loadConnection();
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.dawidfrankiewicz.todo.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/todo_list_api";
    private static final String dbUser = "root";
    private static final String dbPassword = "";
    private static Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

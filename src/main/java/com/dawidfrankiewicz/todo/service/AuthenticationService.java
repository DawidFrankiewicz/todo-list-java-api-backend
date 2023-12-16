package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.database.DatabaseConnection;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private Connection connection;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthenticationService() {
        connection = new DatabaseConnection().getConnection();
    }

    public User getUserByName(String userName) {
        User user = new User();
        String query = "SELECT * FROM users WHERE userName = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = new User();
        String query = "SELECT * FROM users WHERE email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM users";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User currentUser = new User();
                currentUser.setId(resultSet.getInt("id"));
                currentUser.setEmail(resultSet.getString("email"));
                currentUser.setUserName(resultSet.getString("username"));
                currentUser.setPassword(resultSet.getString("password"));

                users.add(currentUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void registerUser(User user) {
        // Check if user with this email already exists
        if(getUserByEmail(user.getEmail()).getEmail() != null) {
            throw new RuntimeException("User with this email already exists");
        }
        // Check if user with this username already exists
        if(getUserByName(user.getUserName()).getUserName() != null) {
            throw new RuntimeException("User with this username already exists");
        }

        String query = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, encodedPassword);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

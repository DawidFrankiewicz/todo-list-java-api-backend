package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.User;
import com.dawidfrankiewicz.todo.database.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Connection connection;

    public AuthenticationService() {
        connection = new DatabaseConnection().getConnection();
    }

    public User getUser(String email) {
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

    public void registerUser(User user) {

        if (getUser(user.getEmail()).getEmail() != null) {
            throw new RuntimeException("User with this email already exists");
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
            //BUG
            e.printStackTrace();
        }
    }

    public int loginUser(User inputData) {

        User user = new User();
        String query = "SELECT * FROM users";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));


                if (user.getUserName().equals(inputData.getUserName()) && passwordEncoder.matches(inputData.getPassword(), user.getPassword())) {
                    return user.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Username or password");
    }
}



package com.dawidfrankiewicz.todo.service;

import com.dawidfrankiewicz.todo.api.model.Status;
import com.dawidfrankiewicz.todo.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserConfigService {
    @Autowired
    private SecurityService securityService;
    private Connection connection;

    public UserConfigService() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Status> getStatuses(int userId) {
        List<Status> statusList = new ArrayList<>();
        String query = "SELECT id,status FROM user_available_statuses WHERE user_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Status newStatus = new Status();
                newStatus.setId(resultSet.getInt("id"));
                newStatus.setStatus(resultSet.getString("status"));
                statusList.add(newStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusList;
    }

    public void addStatus(int userId, Status status) {
        String query = "INSERT INTO user_available_statuses ( user_id,status) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, status.getStatus());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStatus(int userId, int id) {
        String query = "DELETE FROM user_available_statuses WHERE user_id = ? AND id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editStatus(int userId, int id, Status status) {
        String query = "UPDATE user_available_statuses SET status = ? WHERE user_id = ? AND id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status.getStatus());
            statement.setInt(2, userId);
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

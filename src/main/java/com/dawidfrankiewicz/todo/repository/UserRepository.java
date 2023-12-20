package com.dawidfrankiewicz.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dawidfrankiewicz.todo.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAllUsers();

    @Query(value = "SELECT * FROM users WHERE username = ?", nativeQuery = true)
    User findUserByName(String username);

    @Query(value = "SELECT * FROM users WHERE email = ?", nativeQuery = true)
    User findUserByEmail(String email);
}

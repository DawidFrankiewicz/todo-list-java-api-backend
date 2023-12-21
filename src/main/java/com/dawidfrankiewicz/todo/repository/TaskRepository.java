package com.dawidfrankiewicz.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dawidfrankiewicz.todo.api.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM tasks WHERE user_id = ?", nativeQuery = true)
    List<Task> findAllForUser(int userId);

    @Query(value = "SELECT * FROM tasks WHERE user_id = ? AND id = ?", nativeQuery = true)
    Task findByIdForUser(int userId, int id);
}

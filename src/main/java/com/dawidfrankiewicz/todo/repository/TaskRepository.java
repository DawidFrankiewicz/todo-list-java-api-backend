package com.dawidfrankiewicz.todo.repository;

import com.dawidfrankiewicz.todo.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUser_id(int userId);

    Task findByUser_idAndId(int userId, int id);

    void deleteByUser_idAndId(int user_Id, int id);
}

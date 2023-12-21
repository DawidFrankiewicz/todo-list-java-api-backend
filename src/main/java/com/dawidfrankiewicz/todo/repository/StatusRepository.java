package com.dawidfrankiewicz.todo.repository;

import com.dawidfrankiewicz.todo.api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    List<Status> findAllByUser_id(int userId);

    Status findByUser_idAndId(int userId, int id);


    void deleteByUser_idAndId(int user_Id, int id);
}

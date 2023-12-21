package com.dawidfrankiewicz.todo.api.model;

// TODO: Make this class an entity (like Uesr or Task)
public class Status {
    private Integer id;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

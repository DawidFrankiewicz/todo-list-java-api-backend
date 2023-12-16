package com.dawidfrankiewicz.todo.api.model;

public class Task {
    private Integer id;
    private String title;
    private String description;
    private Boolean isDone;

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getIsDone() {
        return this.isDone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
}
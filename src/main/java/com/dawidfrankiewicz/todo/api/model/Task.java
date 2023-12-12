package com.dawidfrankiewicz.todo.api.model;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean done;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public Task(int id, String title, String description, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public int getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean isDone() {
        return this.done;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
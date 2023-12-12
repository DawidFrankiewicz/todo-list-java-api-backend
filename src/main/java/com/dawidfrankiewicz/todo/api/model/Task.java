package com.dawidfrankiewicz.todo.api.model;

import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private boolean done;

    public Task(String title, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public UUID getId() {
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
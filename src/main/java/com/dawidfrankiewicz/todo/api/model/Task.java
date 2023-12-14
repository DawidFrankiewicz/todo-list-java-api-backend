package com.dawidfrankiewicz.todo.api.model;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean isDone;

    public int getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean getIsDone() {
        return this.isDone;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
}
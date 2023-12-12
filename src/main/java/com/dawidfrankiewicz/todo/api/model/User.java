package com.dawidfrankiewicz.todo.api.model;

public class User {
    private int id;
    private String userName;
    private String mail;
    private String password;

    public User(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }
}

package com.dawidfrankiewicz.todo.api.model;

import java.util.regex.Pattern;

public class User {

    private final static String regexEmailPattern = "^(.+)@(\\S+)$";
    private Integer id;
    private String userName;
    private String email;
    private String password;

    public boolean validateEmail() {
        return Pattern.compile(regexEmailPattern)
                .matcher(email)
                .matches();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

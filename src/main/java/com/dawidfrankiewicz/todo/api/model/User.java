package com.dawidfrankiewicz.todo.api.model;

import java.util.regex.Pattern;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class User {
    private final static String regexEmailPattern = "^(.+)@(\\S+)$";

    private Integer id;
    private String userName;
    private String email;
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        setEmail(email);
        this.password = password;
    }

    private boolean validateEmail(String email) {
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
        this.email = validateEmail(email) ? email : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

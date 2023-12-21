package com.dawidfrankiewicz.todo.api.model;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.GenerationType;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    private final static String regexEmailPattern = "^(.+)@(\\S+)$";
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Task> tasks;

    private boolean validateEmail(String email) {
        return Pattern.compile(regexEmailPattern)
                .matcher(email)
                .matches();
    }

    public void setEmail(String email) {
        this.email = validateEmail(email) ? email : null;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
}

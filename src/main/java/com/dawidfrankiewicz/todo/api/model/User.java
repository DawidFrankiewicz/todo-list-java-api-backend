package com.dawidfrankiewicz.todo.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    private String username;
    @NotBlank(message = "Email Address is mandatory")
    @Email(message = "Invalid email")
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Task> tasks;


    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
}

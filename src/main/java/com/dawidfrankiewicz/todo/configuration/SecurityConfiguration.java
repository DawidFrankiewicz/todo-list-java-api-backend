package com.dawidfrankiewicz.todo.configuration;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.dawidfrankiewicz.todo.service.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers("/api/**").hasRole("USER")
            )
            .httpBasic(Customizer.withDefaults()).csrf((csrf) -> csrf.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<com.dawidfrankiewicz.todo.api.model.User> users = authenticationService.getUsers();
        List<UserDetails> usersDetails = new ArrayList<>();
        
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        for (com.dawidfrankiewicz.todo.api.model.User currentUser : users) {
            UserDetails userDetails = User.builder()
                .passwordEncoder((password) -> (String) encoder.encode(password))
                .username(currentUser.getUserName())
                .password(currentUser.getPassword())
                .roles("USER")
                .build();
            usersDetails.add(userDetails);
        }

        return new InMemoryUserDetailsManager(usersDetails);
    }
}
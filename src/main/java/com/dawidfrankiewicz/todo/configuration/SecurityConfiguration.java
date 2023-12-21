package com.dawidfrankiewicz.todo.configuration;

import com.dawidfrankiewicz.todo.security.CustomDetailsService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final WebApplicationContext applicationContext;
    private CustomDetailsService userDetailsService;

    // Get values from application.properties
    @Value("${spring.datasource.driver-class-name}")
    private String springDatasourceDriverClassName;
    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;
    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;
    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;
    
    @PostConstruct
    public void completeSetup() {
        userDetailsService = applicationContext.getBean(CustomDetailsService.class);
    }

    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(springDatasourceDriverClassName);
        dataSource.setUrl(springDatasourceUrl);
        dataSource.setUsername(springDatasourceUsername);
        dataSource.setPassword(springDatasourcePassword);
        return dataSource;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/error").permitAll()
                .requestMatchers("/api/*/auth/register").permitAll()
                .requestMatchers("/api/**").hasRole("USER"))
            .httpBasic(Customizer.withDefaults())
            .csrf((csrf) -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        // TODO: Change deprecated method to lambda () -> {}
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder())
            .and()
            .authenticationProvider(authenticationProvider())
            .build();

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource());
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
}
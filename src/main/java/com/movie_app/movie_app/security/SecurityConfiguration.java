package com.movie_app.movie_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity
            .authorizeHttpRequests() // Updated method for authorizing requests
            .requestMatchers("/api/auth/**").permitAll() // Allow access to login endpoint
            .requestMatchers("/api/movie/**").permitAll()
            .anyRequest().authenticated(); // All other endpoints require authentication
        return http.build(); // Return the configured SecurityFilterChain
    }
    

}

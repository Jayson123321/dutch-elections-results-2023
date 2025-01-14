# Security

## Introduction
This section provides an overview of the security mechanisms implemented in the application. It covers authentication, authorization, and best practices to ensure the application is secure.

## Authentication
For accessing certain endpoints, authentication is required. This application uses token-based authentication with JWT.

### How it Works
1. **User Login**: The user provides their credentials (username and password) to the login endpoint.
2. **Token Generation**: Upon successful authentication, a JWT token is generated and returned to the user.
3. **Token Usage**: The user includes the JWT token in the Authorization header of subsequent requests to access protected endpoints.
4. **Token Validation**: The server validates the token on each request to ensure it is valid and not expired.

### Implementation
- **Login Endpoint**: `/api/auth/login`
  - **Method**: `POST`
  - **Request Body**: JSON object with `username` and `password`
  - **Response**: JSON object with `token`

- **Protected Endpoint Example**: `/api/protected`
  - **Method**: `GET`
  - **Headers**: `Authorization: Bearer <token>`

## Authorization
Authorization ensures that users have the necessary permissions to access specific resources.

### Role-Based Access Control (RBAC)
The application uses RBAC to manage permissions. Users are assigned roles, and each role has specific permissions.

### Implementation
- **Roles**: `USER`, `ADMIN`
- **Permissions**:
  - `USER`: Can access general user endpoints.
  - `ADMIN`: Can access administrative endpoints.

## Security Configuration
The `SecurityConfig` class configures the security settings for the application.

### Configuration Details
- **CSRF Protection**: Disabled for simplicity.
- **Authorization**:
  - All requests to `/api/**` are permitted without authentication.
  - All other requests require authentication.
- **HTTP Basic Authentication**: Enabled for simplicity.

### Code Example
```java
package com.election.backendjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
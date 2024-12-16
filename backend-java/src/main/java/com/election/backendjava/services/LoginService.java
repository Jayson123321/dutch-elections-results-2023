package com.election.backendjava.services;

import com.election.backendjava.dto.LoginRequest;
import com.election.backendjava.dto.LoginResponse;
import com.election.backendjava.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email());
        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.encode(user.getUsername(), user.getId(), user.getRole());
        return new LoginResponse(token);
    }
}
package com.election.backendjava.services;

import com.election.backendjava.dto.RegisterRequest;
import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import jakarta.persistence.Access;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service

public class AuthService {

    @Autowired
    private UserRepository userRepository;
    public void register ( RegisterRequest registerRequest) {
        var foundUser = userRepository.findByEmail(registerRequest.email());
        if (foundUser != null) {
            throw new RuntimeException("Email already taken");
        }
        var user = new User();

        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(hashPassword(registerRequest.password()));
        user.setRole("USER");
        userRepository.save(user);
    }

    // Hash the password
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());}
    // Check if the password matches
     private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);}

}

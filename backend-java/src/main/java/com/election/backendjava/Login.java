//package com.election.backendjava;
//
//import com.election.backendjava.dto.LoginRequest;
//import com.election.backendjava.entities.User;
//import com.election.backendjava.repositories.UserRepository;
//import com.election.backendjava.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class Login {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtService;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest userCredentials) {
//        User userFromDb = userRepository.findByEmailAndPassword(userCredentials.email(), userCredentials.password());
//
//        if (userFromDb != null) {
//            String tokenString = jwtService.encode(userFromDb.getUsername(), userFromDb.getId(), userFromDb.getRole());
//            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
//                    .body("{\"token\": \"" + tokenString + "\", \"message\": \"Login successful\"}");
//        } else {
//            return ResponseEntity.status(401).body("{\"message\": \"Login failed: Incorrect email or password\"}");
//        }
//    }
//}
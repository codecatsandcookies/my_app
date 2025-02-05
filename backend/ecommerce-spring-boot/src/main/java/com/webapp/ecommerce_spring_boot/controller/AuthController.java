package com.webapp.ecommerce_spring_boot.controller;


import com.webapp.ecommerce_spring_boot.dao.UserRepository;
import com.webapp.ecommerce_spring_boot.dto.LoginRequest;
import com.webapp.ecommerce_spring_boot.dto.LoginResponse;
import com.webapp.ecommerce_spring_boot.dto.RegisterRequest;
import com.webapp.ecommerce_spring_boot.entity.User;

import com.webapp.ecommerce_spring_boot.service.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http:localhost:4200")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();

        if (request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            response.put("message", "Email and password cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.put("message", "Email is already in use.");
            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("USER");

        userRepository.save(newUser);

        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response);
    }
}

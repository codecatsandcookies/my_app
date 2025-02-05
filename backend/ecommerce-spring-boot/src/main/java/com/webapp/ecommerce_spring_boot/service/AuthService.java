package com.webapp.ecommerce_spring_boot.service;

import com.webapp.ecommerce_spring_boot.dao.UserRepository;
import com.webapp.ecommerce_spring_boot.dto.LoginRequest;
import com.webapp.ecommerce_spring_boot.dto.LoginResponse;
import com.webapp.ecommerce_spring_boot.dto.RegisterRequest;
import com.webapp.ecommerce_spring_boot.entity.User;
import com.webapp.ecommerce_spring_boot.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public ResponseEntity<Map<String, String>> registerUser(RegisterRequest registerRequest) {
        Map<String, String> response = new HashMap<>();

        if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty() ||
                registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
            response.put("message", "Email and password cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            response.put("message", "Email already registered.");
            return ResponseEntity.badRequest().body(response);
        }

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "USER");

        userRepository.save(newUser);
        response.put("message", "User registered successfully.");
        return ResponseEntity.ok(response);
    }

    // Login user
    public ResponseEntity<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(userOpt.get().getEmail());
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(401).body(null);
    }
}

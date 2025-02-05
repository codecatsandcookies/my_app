package com.webapp.ecommerce_spring_boot;

import com.webapp.ecommerce_spring_boot.dao.UserRepository;
import com.webapp.ecommerce_spring_boot.dto.RegisterRequest;
import com.webapp.ecommerce_spring_boot.entity.User;
import com.webapp.ecommerce_spring_boot.security.JwtUtil;
import com.webapp.ecommerce_spring_boot.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_shouldRegisterSuccessfully() {
        RegisterRequest request = new RegisterRequest("test1@example.com", "password1234", "USER");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        ResponseEntity<Map<String, String>> response = authService.registerUser(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("message", "User registered successfully."), response.getBody());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_shouldReturnError_whenEmailExists() {
        RegisterRequest request = new RegisterRequest("test1@example.com", "password1234", "USER");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        ResponseEntity<Map<String, String>> response = authService.registerUser(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(Map.of("message", "Email already registered."), response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }
}

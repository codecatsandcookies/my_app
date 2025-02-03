package com.webapp.ecommerce_spring_boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email; // Changed from "username" to "email"
    private String password;
    private String role; // Optional: default to "USER"
}

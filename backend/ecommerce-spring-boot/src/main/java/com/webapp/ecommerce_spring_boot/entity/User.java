package com.webapp.ecommerce_spring_boot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // Changed from "username" to "email"

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}

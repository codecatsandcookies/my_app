package com.webapp.ecommerce_spring_boot.dao;

import com.webapp.ecommerce_spring_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}


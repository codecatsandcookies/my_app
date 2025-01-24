package com.webapp.ecommerce_spring_boot.dao;


import com.webapp.ecommerce_spring_boot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}



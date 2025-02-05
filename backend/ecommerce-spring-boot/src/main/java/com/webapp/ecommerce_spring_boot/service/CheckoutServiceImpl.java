package com.webapp.ecommerce_spring_boot.service;

import com.webapp.ecommerce_spring_boot.dao.CustomerRepository;
import com.webapp.ecommerce_spring_boot.dao.OrderRepository;
import com.webapp.ecommerce_spring_boot.dao.ProductRepository;
import com.webapp.ecommerce_spring_boot.dao.UserRepository;
import com.webapp.ecommerce_spring_boot.dto.Purchase;
import com.webapp.ecommerce_spring_boot.dto.PurchaseResponse;
import com.webapp.ecommerce_spring_boot.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public void placeOrder() {
        Authentication auth = getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        String username = auth.getName();
        System.out.println("Authenticated user: " + username);
    }

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;



    public CheckoutServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository,
                               OrderRepository orderRepository,
                               UserRepository userRepository

    ) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // Retrieve the order and customer from DTO
        Order order = purchase.getOrder();
        Customer customer = purchase.getCustomer(); // Ensure customer is retrieved

        // Save customer explicitly before associating with the order
        customer = customerRepository.save(customer);

        // Associate customer with the order
        order.setCustomer(customer);

        // Generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Populate order with orderItems and decrement stock
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID: " + orderItem.getProductId()));

            if (product.getUnitsInStock() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            product.setUnitsInStock(product.getUnitsInStock() - orderItem.getQuantity());
            productRepository.save(product);
        });

        order.setOrderItems(orderItems);

        // Save the order to the database
        orderRepository.save(order);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }


    @Transactional
    public boolean cancelLatestOrder() {
        Optional<Order> latestOrderOpt = orderRepository.findFirstByOrderByDateCreatedDesc();

        if (latestOrderOpt.isEmpty()) {
            return false;
        }

        Order latestOrder = latestOrderOpt.get();

        // Restore stock before deleting order
        for (OrderItem orderItem : latestOrder.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + orderItem.getProductId()));

            product.setUnitsInStock(product.getUnitsInStock() + orderItem.getQuantity());
            productRepository.save(product);
        }

        // Remove order items
        latestOrder.getOrderItems().clear();
        orderRepository.save(latestOrder);

        // Delete the order
        orderRepository.delete(latestOrder);

        return true;
    }
}





package com.webapp.ecommerce_spring_boot.controller;

import com.webapp.ecommerce_spring_boot.dao.ProductRepository;
import com.webapp.ecommerce_spring_boot.dto.Purchase;
import com.webapp.ecommerce_spring_boot.dto.PurchaseResponse;
import com.webapp.ecommerce_spring_boot.entity.Product;
import com.webapp.ecommerce_spring_boot.service.CheckoutService;
import com.webapp.ecommerce_spring_boot.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderServiceImpl orderService;
    private final ProductRepository productRepository;

    public CheckoutController(CheckoutService checkoutService, OrderServiceImpl orderService, OrderServiceImpl orderService1, ProductRepository productRepository) {
        this.checkoutService = checkoutService;
        this.orderService = orderService1;
        this.productRepository = productRepository;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> placeOrder(@RequestBody Purchase purchase) {
        try {
            PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
            return ResponseEntity.ok(purchaseResponse); // Ensure a JSON response is returned
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Checkout failed", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/cancelLatestOrder")
    public ResponseEntity<String> cancelLatestOrder() {
        try {
            orderService.cancelLatestOrder();
            return ResponseEntity.ok("Latest order canceled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling latest order: " + e.getMessage());
        }
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestBody int quantity) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Prevent negative stock
            if (product.getUnitsInStock() < quantity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock for product ID: " + id);
            }

            product.setUnitsInStock(product.getUnitsInStock() - quantity);
            productRepository.save(product);

            return ResponseEntity.ok("Stock updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }

}

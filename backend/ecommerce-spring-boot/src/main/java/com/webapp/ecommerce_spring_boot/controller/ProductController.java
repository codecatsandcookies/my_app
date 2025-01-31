package com.webapp.ecommerce_spring_boot.controller;

import com.webapp.ecommerce_spring_boot.dao.ProductRepository;
import com.webapp.ecommerce_spring_boot.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Product not found"));
        }

        Product product = productOpt.get();
        int quantity = payload.getOrDefault("quantity", 0);

        if (quantity < 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid quantity"));
        }

        product.setUnitsInStock(Math.max(0, product.getUnitsInStock() - quantity));
        productRepository.save(product);

        return ResponseEntity.ok(Map.of("message", "Stock updated", "newStock", product.getUnitsInStock()));
    }
}

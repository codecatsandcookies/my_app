package com.webapp.ecommerce_spring_boot.controller;

import com.webapp.ecommerce_spring_boot.dto.Purchase;
import com.webapp.ecommerce_spring_boot.dto.PurchaseResponse;
import com.webapp.ecommerce_spring_boot.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }

    @DeleteMapping("/cancelLatestOrder")
    public ResponseEntity<String> cancelLatestOrder() {
        boolean success = checkoutService.cancelLatestOrder();

        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders available to cancel.");
        }

        return ResponseEntity.ok("Latest order successfully canceled.");
    }

}

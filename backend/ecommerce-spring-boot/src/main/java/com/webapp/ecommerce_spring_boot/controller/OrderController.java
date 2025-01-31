package com.webapp.ecommerce_spring_boot.controller;

import com.webapp.ecommerce_spring_boot.dto.OrderReportDTO;
import com.webapp.ecommerce_spring_boot.service.OrderService;
import com.webapp.ecommerce_spring_boot.service.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping("/cancelLatestOrder")
    public ResponseEntity<String> cancelLatestOrder() {
        String response = orderService.cancelLatestOrder();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<OrderReportDTO>> getOrderReports() {
        List<OrderReportDTO> reports = orderService.getOrderReports();
        return ResponseEntity.ok(reports);
    }
}
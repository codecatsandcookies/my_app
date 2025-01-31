package com.webapp.ecommerce_spring_boot.service;

import com.webapp.ecommerce_spring_boot.dao.OrderRepository;
import com.webapp.ecommerce_spring_boot.dto.OrderReportDTO;
import com.webapp.ecommerce_spring_boot.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;



    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<OrderReportDTO> getOrderReports() {
        return orderRepository.getOrderReports();
    }

    @Override
    public String cancelLatestOrder() {
        Optional<Order> latestOrder = orderRepository.findFirstByOrderByDateCreatedDesc();
        if (latestOrder.isPresent()) {
            orderRepository.delete(latestOrder.get());
            return "Latest order canceled successfully.";
        } else {
            return "No order found to cancel.";
        }
    }
}

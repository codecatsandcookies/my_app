package com.webapp.ecommerce_spring_boot.dto;

import lombok.Data;

import lombok.Data;

import java.util.UUID;

@Data
public class PurchaseResponse {

    private final String orderTrackingNumber = UUID.randomUUID().toString();

    public PurchaseResponse(String orderTrackingNumber) {

    }
}

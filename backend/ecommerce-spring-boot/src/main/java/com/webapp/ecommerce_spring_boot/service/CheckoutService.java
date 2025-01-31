package com.webapp.ecommerce_spring_boot.service;

import com.webapp.ecommerce_spring_boot.dto.Purchase;
import com.webapp.ecommerce_spring_boot.dto.PurchaseResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    @Transactional
    boolean cancelLatestOrder();
}

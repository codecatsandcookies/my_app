package com.webapp.ecommerce_spring_boot.dto;

import com.webapp.ecommerce_spring_boot.entity.Address;
import com.webapp.ecommerce_spring_boot.entity.Customer;
import com.webapp.ecommerce_spring_boot.entity.Order;
import com.webapp.ecommerce_spring_boot.entity.OrderItem;
import lombok.Data;


import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
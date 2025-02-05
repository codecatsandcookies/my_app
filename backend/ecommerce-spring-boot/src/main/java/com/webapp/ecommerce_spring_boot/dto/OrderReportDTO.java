package com.webapp.ecommerce_spring_boot.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderReportDTO {

    private Long orderId;
    private String customerName;
    private Date dateCreated;
    private BigDecimal totalPrice;


    public OrderReportDTO(Long orderId, String customerName, Date dateCreated, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
    }
}
package com.webapp.ecommerce_spring_boot.service;

import com.webapp.ecommerce_spring_boot.dto.OrderReportDTO;

import java.util.List;

public interface OrderService {
    List<OrderReportDTO> getOrderReports();


    String cancelLatestOrder();

}

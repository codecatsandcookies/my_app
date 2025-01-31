package com.webapp.ecommerce_spring_boot.dao;

import com.webapp.ecommerce_spring_boot.dto.OrderReportDTO;
import com.webapp.ecommerce_spring_boot.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);

    Optional<Order> findFirstByOrderByDateCreatedDesc();

    @Query("SELECT new com.webapp.ecommerce_spring_boot.dto.OrderReportDTO(" +
            "o.id, CONCAT(c.firstName, ' ', c.lastName), o.dateCreated, o.totalPrice) " +
            "FROM Order o " +
            "JOIN o.customer c")
    List<OrderReportDTO> getOrderReports();
}

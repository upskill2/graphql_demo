package com.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "sales_orders")
@Getter
@Setter
public class SalesOrderEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @JoinColumn (name = "customer_uuid", nullable = false)
    private CustomerEntity customerEntity;
    private LocalDateTime orderDateTime;
    private String orderNumber;

    @OneToMany (mappedBy = "salesOrderEntity")
    private List<SalesOrderItemsEntity> salesOrderItemsEntity;
}

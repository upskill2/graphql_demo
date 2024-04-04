package com.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "sales_order_items")
@Getter
@Setter
public class SalesOrderItemsEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private int quantity;
/*    @ManyToOne
    @JoinColumn (name = "sales_order_uuid", nullable = false)
    private SalesOrderEntity salesOrderEntity;*/
    UUID modelUuid;

}

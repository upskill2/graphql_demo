package com.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "sales_orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderEntity {

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne
    @JoinColumn (name = "customer_uuid", nullable = false)
    private CustomerEntity customerEntity;

    @CreationTimestamp
    private OffsetDateTime orderDateTime;

    @Column (unique = true)
    private String orderNumber;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn (name = "sales_order_uuid")
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    @Fetch (FetchMode.SUBSELECT)
    private List<SalesOrderItemsEntity> salesOrderItems;

    @OneToOne (fetch = FetchType.EAGER, mappedBy = "salesOrderEntity")
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    private FinanceEntity finance;
}

package com.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "finances")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private double baseAmount;
    private double taxAmount;
    private double discountAmount;
    private boolean isLoan;

    @OneToOne
    @JoinColumn (name = "sales_order_uuid", nullable = false)
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    @Fetch (org.hibernate.annotations.FetchMode.JOIN)
    private SalesOrderEntity salesOrderEntity;

    @OneToOne (fetch = FetchType.LAZY, mappedBy = "financeEntity")
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    private LoanEntity loanEntity;

}

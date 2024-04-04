package com.course.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "loans")
@Getter
@Setter
@Builder
public class LoanEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String financeCompany;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;

    @OneToOne
    @JoinColumn (name = "finance_uuid")
    private FinanceEntity financeEntity;
}

package com.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @GeneratedValue
    @Id
    private UUID uuid;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;

    @OneToMany //(mappedBy = "customerEntity")
    @JoinColumn (name = "customer_uuid")
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    @Fetch (FetchMode.SUBSELECT)
    private List<AddressEntity> addresses;

    @OneToMany (fetch = FetchType.EAGER) //(mappedBy = "customerEntity")
    @JoinColumn (name = "customer_uuid")
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    @Fetch (FetchMode.SUBSELECT)
    private List<DocumentEntity> documentEntity;

    @OneToMany (mappedBy = "customerEntity", fetch = FetchType.LAZY)
    @Cascade (org.hibernate.annotations.CascadeType.ALL)
    private List<SalesOrderEntity> salesOrders;
}

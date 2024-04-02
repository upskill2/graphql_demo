package com.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String street;
    private String city;
    private String zipcode;


/*    @ManyToOne
    @JoinColumn (name = "customer_uuid", nullable = true)
    private CustomerEntity customerEntity;*/

}

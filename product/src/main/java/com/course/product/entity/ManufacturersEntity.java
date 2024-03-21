package com.course.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "manufacturers")
public class ManufacturersEntity {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String name;
    private String originCountry;
    private String description;
}

package com.course.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "engines")
public class EnginesEntity {

    @Id
    @Column (name = "uuid")
    private UUID id;
    private String description;
    private int horsePower;
    private int torque;
    @Column (name = "capacity_cc")
    private int capacityCc;
}

package com.course.product.entity;

import jakarta.persistence.*;
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
    @GeneratedValue
    private UUID uuid;
    private String description;
    private int horsePower;
    private int torque;
    @Column (name = "capacity_cc")
    private int capacityCc;
}

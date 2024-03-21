package com.course.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "series")
public class SeriesEntity {

    @Id
    @Column (name = "uuid")
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @JoinColumn (name = "manufacturer_uuid", nullable = false)
    private ManufacturersEntity manufacturerId;
    private String name;

}

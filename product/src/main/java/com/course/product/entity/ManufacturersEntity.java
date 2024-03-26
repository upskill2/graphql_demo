package com.course.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
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

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "manufacturerId")
    @Fetch (FetchMode.SUBSELECT)
    private List<SeriesEntity> series;
}

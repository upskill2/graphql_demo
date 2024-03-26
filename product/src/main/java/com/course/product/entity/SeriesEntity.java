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
@Table (name = "series")
public class SeriesEntity {

    @Id
    @Column (name = "uuid")
    @GeneratedValue
    private UUID uuid;
    private String name;

    @ManyToOne
    @JoinColumn (name = "manufacturer_uuid", nullable = false)
    private ManufacturersEntity manufacturerId;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "seriesEntity")
    @Fetch (FetchMode.SUBSELECT)
    private List<CharacteristicsEntity> characteristics;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "seriesEntity")
    @Fetch (FetchMode.SUBSELECT)
    private List<ModelsEntity> models;

}

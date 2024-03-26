package com.course.product.entity;

import com.course.product.domain.Enums.*;
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
@Table (name = "models")
public class ModelsEntity {

    @Id
    @Column (name = "uuid")
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @JoinColumn (name = "series_uuid", nullable = false)
    private SeriesEntity seriesEntity;
    private String name;
    private double onTheRoadPrice;
    @Column (name = "length_mm")
    private int lengthMm;
    @Column (name = "width_mm")
    private int widthMm;
    @Column (name = "height_mm")
    private int heightMm;
    private String exteriorColor;
    private String interiorColor;
    private int releaseYear;
    @Enumerated (EnumType.STRING)
    private Transmission transmission;
    @Enumerated (EnumType.STRING)
    private BodyType bodyType;
    @Enumerated (EnumType.STRING)
    private Fuel fuel;
    private int doors;
    private int airbags;
    private boolean isAvailable;
    @OneToOne
    @JoinColumn (name = "engine_uuid", nullable = false)
    private EnginesEntity enginesEntity;

    @OneToMany (mappedBy = "modelsEntity")
    @Fetch (FetchMode.SUBSELECT)
    private List<FeaturesEntity> featuresEntity;

}

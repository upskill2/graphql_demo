package com.course.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "features")
public class FeaturesEntity {
    @Id
    @Column (name = "uuid")
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @JoinColumn (name = "model_uuid", nullable = false)
    private ModelsEntity modelsEntity;
    private String name;
    private boolean activeByDefault;
    private boolean activeByRequest;
    private double installationPrice;
    private boolean isSafety;
    private boolean isEntertainment;
    private boolean isPerformance;
    private boolean isConvenience;
    private boolean isDisplay;
}

package com.course.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table (name = "characteristics")
public class CharacteristicsEntity {
    @Id
    @Column (name = "uuid")
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @JoinColumn (name = "series_uuid", nullable = false)
    private SeriesEntity seriesEntity;
    private String name;

    @Override
    public String toString () {
        return "CharacteristicsEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}

package com.course.product.repository;

import com.course.product.entity.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesEntity, UUID> {
    //  @Query (nativeQuery = true, value = "SELECT * FROM series WHERE lower(name) LIKE lower(:name)")
    @Query (nativeQuery = true, value = "SELECT * FROM series WHERE lower(name) LIKE lower(:name)")
    List<SeriesEntity> findSeriesByNameIgnoreCase (@Param ("name") String name);

    @Query (nativeQuery = true, value = "SELECT * FROM series WHERE manufacturer_uuid = :manufacturerId")
    List<SeriesEntity> findSeriesByManufacturerUuid (@Param ("manufacturerId") UUID manufacturerId);

    @Query (nativeQuery = true, value = "SELECT * FROM series WHERE manufacturer_uuid in (:manufacturerId)")
    List<SeriesEntity> findSeriesByManufacturerUuid (@Param ("manufacturerId") List<UUID> manufacturerId);
}

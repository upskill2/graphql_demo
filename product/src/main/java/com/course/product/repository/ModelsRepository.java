package com.course.product.repository;

import com.course.product.entity.ModelsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelsRepository extends JpaRepository<ModelsEntity, UUID> {

    @Query (nativeQuery = true, value = "SELECT * FROM models WHERE series_uuid IN (:seriesUUIDList)")
    List<ModelsEntity> findModelsBySeriesUUID (@Param ("seriesUUIDList") List<UUID> seriesUUIDList);

    List<ModelsEntity> findByName (String name);
}

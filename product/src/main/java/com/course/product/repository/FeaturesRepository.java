package com.course.product.repository;

import com.course.product.entity.FeaturesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeaturesRepository extends CrudRepository<FeaturesEntity, UUID> {
}

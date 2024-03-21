package com.course.product.repository;

import com.course.product.entity.EnginesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnginesRepository extends CrudRepository<EnginesEntity, UUID> {
}

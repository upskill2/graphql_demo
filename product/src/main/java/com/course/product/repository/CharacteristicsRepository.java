package com.course.product.repository;

import com.course.product.entity.CharacteristicsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CharacteristicsRepository extends CrudRepository<CharacteristicsEntity, UUID>{
}

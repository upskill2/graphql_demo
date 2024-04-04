package com.course.repositories;

import com.course.entity.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity, UUID>,
        JpaSpecificationExecutor<SalesOrderEntity> {
}

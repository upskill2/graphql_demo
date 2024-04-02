package com.course.repositories;

import com.course.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID>,
        JpaSpecificationExecutor<AddressEntity> {

}

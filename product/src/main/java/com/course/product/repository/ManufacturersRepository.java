package com.course.product.repository;

import com.course.product.entity.ManufacturersEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ManufacturersRepository extends CrudRepository<ManufacturersEntity, UUID>, JpaSpecificationExecutor<ManufacturersEntity> {

    @Query (nativeQuery = true, value = "SELECT * FROM manufacturers WHERE lower(name) LIKE lower(:name)")
    public List<ManufacturersEntity> findByName (@Param ("name") String name);

    List<ManufacturersEntity> findAllByOriginCountryIgnoreCase (String originCountry);
}

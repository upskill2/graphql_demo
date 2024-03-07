package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Solutions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends CrudRepository<Solutions, UUID> {


    @Query (nativeQuery = true,
            value = "select * from solutions where lower(content) like lower(:keyword);")
    List<Solutions> findByKeyword (@Param ("keyword") String keyword);
}

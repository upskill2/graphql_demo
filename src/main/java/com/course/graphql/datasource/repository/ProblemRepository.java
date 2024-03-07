package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Problems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemRepository extends CrudRepository<Problems, UUID> {

    List<Problems> findAllByOrderByCreationTimestampDesc ();

    @Query (value = "select * from problems"
            + " where lower(title) like lower(:keyword)"
            + " or lower(content) like lower(:keyword)"
            + " or lower(tags) like lower(:keyword);",
            nativeQuery = true)
    List<Problems> findByKeyword (@Param ("keyword") String keyword);
}

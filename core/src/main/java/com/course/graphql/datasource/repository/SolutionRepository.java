package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Solutions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends CrudRepository<Solutions, UUID> {

    @Query (nativeQuery = true,
            value = "select * from solutions where lower(content) like lower(:keyword);")
    List<Solutions> findByKeyword (@Param ("keyword") String keyword);


   @Transactional
   @Modifying
    @Query (nativeQuery = true,
            value = "update solutions set vote_bad_count = vote_bad_count + 1 where id = :solutionId")
    void addVoteBadCount(@Param ("solutionId") UUID solutionId);

    @Transactional
    @Modifying
    @Query (nativeQuery = true,
            value = "update solutions set vote_good_count = vote_good_count + 1 where id = :solutionId")
    void addVoteGoodCount(@Param ("solutionId") UUID solutionId);

}

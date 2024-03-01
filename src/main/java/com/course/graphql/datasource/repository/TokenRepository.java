package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Tokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends CrudRepository<Tokens, UUID> {
}

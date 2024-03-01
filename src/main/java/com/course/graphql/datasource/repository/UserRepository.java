package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<Users, UUID> {

}

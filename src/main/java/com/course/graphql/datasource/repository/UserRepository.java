package com.course.graphql.datasource.repository;

import com.course.graphql.datasource.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<Users, UUID> {

    public Optional<Users> findUsersByUsernameIgnoreCase (String name);

    @Query ("select u from Users u join Tokens t on u.id = t.userId where t.authToken = :token" +
            " and t.expiryTime > current_timestamp")
    Optional<Users> findUserByToken (@Param ("token") String token);

}

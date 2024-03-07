package com.course.graphql.service.query;

import com.course.graphql.datasource.entity.Users;
import com.course.graphql.datasource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersQueryService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Users> findUserByAuthToken(String authToken) {
        return userRepository.findUserByToken(authToken);
    }
}

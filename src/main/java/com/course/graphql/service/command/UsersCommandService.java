package com.course.graphql.service.command;

import com.course.graphql.datasource.entity.Tokens;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.datasource.repository.TokenRepository;
import com.course.graphql.datasource.repository.UserRepository;
import com.course.graphql.exception.UserAuthenticationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.course.graphql.util.HashUtil.isBcryptMatches;

@Service
public class UsersCommandService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public Tokens login (String username, String password) {
        final Optional<Users> usersQueryResult = userRepository.findUsersByUsernameIgnoreCase (username);

        if (usersQueryResult.isEmpty () || !isBcryptMatches (password, usersQueryResult.get ().getHashedPassword ())) {
            throw new UserAuthenticationException ();
        }

        String randomAuthToken = RandomStringUtils.randomAlphanumeric (40);


        return refreshToken (usersQueryResult.get ().getId (), randomAuthToken);
    }

    private Tokens refreshToken (UUID userId, String authToken) {
        Tokens token = Tokens.builder ()
                .userId (userId)
                .authToken (authToken)
                .creationTime (LocalDateTime.now ())
                .expiryTime (LocalDateTime.now ().plusHours (2))
                .build ();

        return tokenRepository.save (token);
    }

}

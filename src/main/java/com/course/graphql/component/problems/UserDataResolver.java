package com.course.graphql.component.problems;

import com.course.graphql.datasource.entity.Tokens;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.generated.types.*;
import com.course.graphql.service.command.UsersCommandService;
import com.course.graphql.service.query.UsersQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@DgsComponent
public class UserDataResolver {

    @Autowired
    private UsersCommandService usersCommandService;

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private GraphqlBeanMapper mapper;

    @DgsQuery //(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Login)
    public User accountInfo (@RequestHeader (name = "authToken", required = true) String authToken) {
        return usersQueryService.findUserByAuthToken (authToken)
                .map (mapper::toGraphqlUser)
                .orElseThrow (() -> new DgsEntityNotFoundException ("User not found"));
    }

    @DgsMutation
    public UserResponse createUser (@InputArgument (name = "user") UserCreateInput userCreateInput) {
        return new UserResponse ();
    }

    @DgsMutation
    public UserResponse userLogin (@InputArgument (name = "user") UserLoginInput userLoginInput) {
        Tokens generatedToken = usersCommandService.login (userLoginInput.getUsername (), userLoginInput.getPassword ());
        return UserResponse.newBuilder ()
                .user (accountInfo (generatedToken.getAuthToken ()))
                .token (mapper.toGraphqlToken (generatedToken))
                .build ();
    }

    @DgsMutation
    public UserActivationResponse userActivation (@InputArgument (name = "user") UserActivationInput userActivationInput) {
        return new UserActivationResponse ();

    }

}

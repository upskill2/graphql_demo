package com.course.graphql.component.problems;

import com.course.graphql.datasource.UserRole;
import com.course.graphql.datasource.entity.Tokens;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.exception.UserAuthenticationException;
import com.course.graphql.generated.types.*;
import com.course.graphql.service.command.UsersCommandService;
import com.course.graphql.service.query.UsersQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    @Autowired
    private UsersCommandService usersCommandService;

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private GraphqlBeanMapper mapper;

    @DgsMutation
    @Secured ("ROLE_ADMIN")
    public UserResponse createUser (
            @RequestHeader (name = "authToken", required = false) String authToken,
            @InputArgument (name = "user") UserCreateInput userCreateInput) {

        if (authToken == null || authToken.isEmpty ()) {
            authToken = "";
        }

        Users adminUser = usersQueryService.findUserByAuthToken (authToken)
                .orElseThrow (() -> new DgsEntityNotFoundException ("User not found"));

        //    if (adminUser.getUserRole () == UserRole.ROLE_ADMIN) {
        final Users newUser = usersCommandService.createNewUsers (mapper.toEntityUsers (userCreateInput));
        final Tokens generatedToken = usersCommandService.login (newUser.getUsername (), userCreateInput.getPassword ());

        return UserResponse.newBuilder ()
                .user (mapper.toGraphqlUser (newUser))
                .token (mapper.toGraphqlToken (generatedToken))
                .build ();
        //      }
        //       else {
        //          throw new UserAuthenticationException ("User not authorized to perform this action");
        //     }
    }


    @DgsQuery //(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Login)
    public User accountInfo (@RequestHeader (name = "authToken", required = true) String authToken) {
        return usersQueryService.findUserByAuthToken (authToken)
                .map (mapper::toGraphqlUser)
                .orElseThrow (() -> new DgsEntityNotFoundException ("User not found"));
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
    public UserActivationResponse userActivation (
            @RequestHeader (name = "authToken", required = false) String authToken,
            @InputArgument (name = "user") UserActivationInput userActivationInput) {

        if (authToken == null || authToken.isEmpty ()) {
            authToken = "";
        }

        Users user = usersQueryService.findUserByAuthToken (authToken)
                .orElseThrow (() -> new DgsEntityNotFoundException ("User not found"));

        if (user.getUserRole () == UserRole.ROLE_ADMIN) {
            final boolean activated = usersCommandService.activateUser (userActivationInput.getUsername (), userActivationInput.getActive ());
            return UserActivationResponse.newBuilder ()
                    .isActive (activated)
                    .build ();
        }
        throw new UserAuthenticationException ("User not authorized to perform this action");
    }

}

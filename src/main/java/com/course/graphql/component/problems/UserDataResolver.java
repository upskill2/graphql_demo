package com.course.graphql.component.problems;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.*;
import com.netflix.graphql.dgs.*;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {


    @DgsQuery //(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Login)
    public User accountInfo (@RequestHeader (name = "authToken", required = true) String authToken) {
        return new User ();
    }

    @DgsMutation
    public UserResponse createUser (@InputArgument (name = "user") UserCreateInput userCreateInput) {
        return new UserResponse ();
    }

    @DgsMutation
    public UserResponse userLogin (@InputArgument (name = "user") UserLoginInput userLoginInput) {
        return new UserResponse ();
    }

    @DgsMutation
    public UserActivationResponse userActivation (@InputArgument(name = "user") UserActivationInput userActivationInput) {
        return new UserActivationResponse ();

    }

}

package com.course.graphql.exception;

public class UserAuthenticationException extends RuntimeException {
    public UserAuthenticationException () {
        super ("User authentication failed. Invalid credentials");
    }

    public UserAuthenticationException (final String message) {
        super (message);
    }


}

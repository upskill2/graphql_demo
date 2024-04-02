package com.course.graphql.exception;

public class ProblemsPermissionsException extends RuntimeException {
    public ProblemsPermissionsException () {
        super ();
    }

    public ProblemsPermissionsException (String message) {
        super (message);
    }
}

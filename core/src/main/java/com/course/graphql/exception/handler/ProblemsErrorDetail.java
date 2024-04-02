package com.course.graphql.exception.handler;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

public class ProblemsErrorDetail implements ErrorDetail {
    @Override
    public ErrorType getErrorType () {
        return ErrorType.UNAUTHENTICATED;
    }
    @Override
    public String toString () {
        return "User validation failed. Please check your credentials and try again.";
    }
}

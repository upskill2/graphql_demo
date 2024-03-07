package com.course.graphql.exception.handler;

import com.course.graphql.exception.UserAuthenticationException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ProblemsGraphqlExceptionHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultDataFetcherExceptionHandler;

    public ProblemsGraphqlExceptionHandler () {
        defaultDataFetcherExceptionHandler = new DefaultDataFetcherExceptionHandler ();
    }

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException (
            final DataFetcherExceptionHandlerParameters handlerParameters) {

        final Throwable exception = handlerParameters.getException ();

        if (exception instanceof UserAuthenticationException) {
            TypedGraphQLError error = TypedGraphQLError.newBuilder ()
                    .message (exception.getMessage ())
                    .path (handlerParameters.getPath ())
                    .errorDetail (new ProblemsErrorDetail ())
                    .build ();

            final DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult ()
                    .error (error)
                    .build ();

            return CompletableFuture.completedFuture (result);
        }
        return defaultDataFetcherExceptionHandler.handleException (handlerParameters);
    }

}

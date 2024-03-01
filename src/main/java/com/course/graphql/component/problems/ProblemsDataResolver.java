package com.course.graphql.component.problems;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.course.graphql.generated.types.ProblemResponse;
import com.netflix.graphql.dgs.*;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;

@DgsComponent
public class ProblemsDataResolver {

    @DgsQuery (field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemsLatestList () {
        return null;
    }

    @DgsQuery (field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemById (@InputArgument (name = "id") String problemId) {
        return null;
    }

    @DgsMutation
    public ProblemResponse problemCreate (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument (name = "problem") ProblemCreateInput problemCreateInput) {
        return new ProblemResponse ();
    }

    @DgsSubscription (field = DgsConstants.SUBSCRIPTION.ProblemsAdded)
    public Flux<Problem> subscribeToProblemsAdded () {
        return null;
    }

}

package com.course.graphql.component.problems;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.course.graphql.generated.types.ProblemResponse;
import com.course.graphql.service.query.ProblemsQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.stream.Collectors.toList;

@DgsComponent
@RequiredArgsConstructor
public class ProblemsDataResolver {

    private final ProblemsQueryService problemsService;
    private final GraphqlBeanMapper mapper;

    @DgsQuery (field = DgsConstants.QUERY.ProblemLatestList)


    public List<Problem> getProblemsLatestList () {
        return problemsService.getAllProblemsOrdered ().stream ().map (mapper::toGraphqlProblem).toList ();
    }

    @DgsQuery (field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemById (@InputArgument (name = "id") String problemId) {
        return problemsService.findProblemById (problemId).map (mapper::toGraphqlProblem).orElse (null);
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

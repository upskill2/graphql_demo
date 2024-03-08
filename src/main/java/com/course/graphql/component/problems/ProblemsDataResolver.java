package com.course.graphql.component.problems;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.exception.UserAuthenticationException;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.ProblemCreateInput;
import com.course.graphql.generated.types.ProblemResponse;
import com.course.graphql.generated.types.User;
import com.course.graphql.service.command.ProblemsCommandService;
import com.course.graphql.service.query.ProblemsQueryService;
import com.course.graphql.service.query.UsersQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class ProblemsDataResolver {

    private final ProblemsQueryService problemsService;
    private final GraphqlBeanMapper mapper;
    private final ProblemsCommandService problemsCommandService;
    private final UsersQueryService usersQueryService;

    @DgsQuery (field = DgsConstants.QUERY.ProblemLatestList)


    public List<Problem> getProblemsLatestList () {
        return problemsService.getAllProblemsOrdered ().stream ().map (mapper::toGraphqlProblem).toList ();
    }

    @DgsQuery (field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemById (@InputArgument (name = "id") String problemId) {
        return problemsService.findProblemById (problemId).map (mapper::toGraphqlProblem)
                .orElseThrow (DgsEntityNotFoundException::new);
    }

    @DgsMutation
    public ProblemResponse problemCreate (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument (name = "problem") ProblemCreateInput problemCreateInput) {

        final Users user = usersQueryService.findUserByAuthToken (authToken)
                .orElseThrow (() -> new UserAuthenticationException ("User not found"));

        Problems problem = mapper.toGraphqlProblemCreateInput (problemCreateInput, user);
        final Problems problemCreated = problemsCommandService.createProblem (problem);
        return mapper.toGraphqlProblemCreateResponse (problemCreated);
    }

    @DgsSubscription (field = DgsConstants.SUBSCRIPTION.ProblemsAdded)
    public Flux<Problem> subscribeToProblemsAdded () {
        return problemsCommandService.problemsFlux ().map (mapper::toGraphqlProblem);
    }

}

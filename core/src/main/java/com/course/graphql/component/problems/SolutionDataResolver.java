package com.course.graphql.component.problems;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.datasource.repository.SolutionRepository;
import com.course.graphql.exception.UserAuthenticationException;
import com.course.graphql.generated.types.*;
import com.course.graphql.service.command.SolutionsCommandService;
import com.course.graphql.service.command.UsersCommandService;
import com.course.graphql.service.query.ProblemsQueryService;
import com.course.graphql.service.query.SolutionsQueryService;
import com.course.graphql.service.query.UsersQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class SolutionDataResolver {

    @Autowired
    private SolutionsCommandService solutionsCommandService;

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private ProblemsQueryService problemsQueryService;

    @Autowired
    SolutionsQueryService solutionsQueryService;

    @Autowired
    private GraphqlBeanMapper mapper;

    @DgsMutation
    public SolutionResponse createSolution (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument (name = "solution") SolutionCreateInput solutionCreateInput) {

        Users user = usersQueryService.findUserByAuthToken (authToken)
                .orElseThrow (UserAuthenticationException::new);
        Problems problem = problemsQueryService.findProblemById (solutionCreateInput.getProblemId ())
                .orElseThrow (DgsEntityNotFoundException::new);


        Solutions solutions = mapper.toEntitylSolutions (solutionCreateInput, user, problem);
        final Solutions createdSolution = solutionsCommandService.createSolution (solutions);

        final SolutionResponse solutionResponse = mapper.toSolutionResponse (createdSolution);
        return solutionResponse;
    }

    @DgsMutation
    public SolutionResponse voteSolution (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument SolutionVoteInput vote) {

        Users user = usersQueryService.findUserByAuthToken (authToken)
                .orElseThrow (UserAuthenticationException::new);

        SolutionResponse solutionResponse = new SolutionResponse ();
        final UUID solutionId = UUID.fromString (vote.getSolutionId ());

        final Optional<Solutions> solutions = vote.getVoteAsGood () ?
                solutionsCommandService.addVoteGoodCount (solutionId) : solutionsCommandService.addVoteBadCount (solutionId);

        final Solution graphqlSolution = mapper.toGraphqlSolution (solutions.orElseThrow (DgsEntityNotFoundException::new));
        solutionResponse.setSolution (graphqlSolution);
        return solutionResponse;
    }


    @DgsSubscription
    public Flux<Solution> solutionVoteChanged (@InputArgument String solutionId) {
        Solutions solution = solutionsQueryService.findSolutionById (solutionId)
                .orElseThrow (DgsEntityNotFoundException::new);
        return solutionsCommandService.solutionsFlux ().map (mapper::toGraphqlSolution);

    }

}

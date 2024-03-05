package com.course.graphql.component.problems;

import com.course.graphql.generated.types.Solution;
import com.course.graphql.generated.types.SolutionCreateInput;
import com.course.graphql.generated.types.SolutionResponse;
import com.course.graphql.generated.types.SolutionVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

    @DgsMutation
    public SolutionResponse createSolution (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument (name = "solution") SolutionCreateInput solutionCreateInput) {
        return new SolutionResponse ();
    }

    @DgsMutation
    public SolutionResponse voteSolution (
            @RequestHeader (name = "authToken", required = true) String authToken,
            @InputArgument SolutionVoteInput vote) {
        return new SolutionResponse ();
    }

    @DgsSubscription
    public Flux<Solution> solutionVoteChanged (@InputArgument String solutionId) {
        return null;
    }

}

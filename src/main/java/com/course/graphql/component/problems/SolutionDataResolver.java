package com.course.graphql.component.problems;

import com.course.graphql.generated.types.Solution;
import com.course.graphql.generated.types.SolutionCreateInput;
import com.course.graphql.generated.types.SolutionResponse;
import com.course.graphql.generated.types.SolutionVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

    @DgsMutation
    public SolutionResponse createSolution (@InputArgument (name = "solution") SolutionCreateInput solutionCreateInput) {
        return new SolutionResponse ();
    }

    @DgsMutation
    public SolutionResponse voteSolution (@InputArgument SolutionVoteInput vote) {
        return new SolutionResponse ();
    }

    @DgsSubscription
    public Flux<Solution> solutionVoteChanged () {
        return null;
    }

}

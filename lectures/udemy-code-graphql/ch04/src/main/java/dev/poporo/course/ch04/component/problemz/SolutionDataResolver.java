package dev.poporo.course.ch04.component.problemz;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.MUTATION;
import com.netflix.dgs.codegen.generated.DgsConstants.SUBSCRIPTION;
import com.netflix.dgs.codegen.generated.types.Solution;
import com.netflix.dgs.codegen.generated.types.SolutionCreateInput;
import com.netflix.dgs.codegen.generated.types.SolutionResponse;
import com.netflix.dgs.codegen.generated.types.SolutionVoteInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.SolutionCreate)
    public SolutionResponse  createSolution(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput
    ) {
        return null;
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.SolutionVote)
    public SolutionResponse createSolutionVote(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput
    ) {
        return null;
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
        return null;
    }
}

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
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import dev.poporo.course.ch04.datasource.problemz.entity.Solutionz;
import dev.poporo.course.ch04.exception.ProblemzAuthenticationException;
import dev.poporo.course.ch04.service.command.SolutionzCommandService;
import dev.poporo.course.ch04.service.query.ProblemzQueryService;
import dev.poporo.course.ch04.service.query.UserzQueryService;
import dev.poporo.course.ch04.util.GraphqlBeanMapper;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class SolutionDataResolver {

    @Autowired
    private UserzQueryService userzQueryService;

    @Autowired
    private ProblemzQueryService problemzQueryService;

    @Autowired
    private SolutionzCommandService solutionzCommandService;

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.SolutionCreate)
    public SolutionResponse  createSolution(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "solution") SolutionCreateInput solutionCreateInput
    ) {
        var userz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);
        var problemzId = UUID.fromString(solutionCreateInput.getProblemId());
        var problemz = problemzQueryService.problemzDetail(problemzId)
                .orElseThrow(DgsEntityNotFoundException::new);
        var solutionz = GraphqlBeanMapper.mapToEntity(solutionCreateInput, userz, problemz);
        var created = solutionzCommandService.createSolutionz(solutionz);

        return SolutionResponse.newBuilder()
                .solution(GraphqlBeanMapper.mapToGraphql(created))
                .build();
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.SolutionVote)
    public SolutionResponse createSolutionVote(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "vote") SolutionVoteInput solutionVoteInput
    ) {
        Optional<Solutionz> updated;
        userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);

        if (solutionVoteInput.getVoteAsGood()) {
            updated = solutionzCommandService.voteGood(
                    UUID.fromString(solutionVoteInput.getSolutionId())
            );
        } else {
            updated = solutionzCommandService.voteBad(
                    UUID.fromString(solutionVoteInput.getSolutionId())
            );
        }

        if (updated.isEmpty()) {
            throw new DgsEntityNotFoundException("Solution " + solutionVoteInput.getSolutionId() + " not found");
        }

        return SolutionResponse.newBuilder()
                .solution(GraphqlBeanMapper.mapToGraphql(updated.get()))
                .build();
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = SUBSCRIPTION.SolutionVoteChanged)
    public Flux<Solution> subscribeSolutionVote(@InputArgument(name = "solutionId") String solutionId) {
        return solutionzCommandService.solutionzFlux().map(GraphqlBeanMapper::mapToGraphql);
    }
}

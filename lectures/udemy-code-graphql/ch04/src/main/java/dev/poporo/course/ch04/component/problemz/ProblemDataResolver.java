package dev.poporo.course.ch04.component.problemz;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.MUTATION;
import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.dgs.codegen.generated.DgsConstants.SUBSCRIPTION;
import com.netflix.dgs.codegen.generated.types.Problem;
import com.netflix.dgs.codegen.generated.types.ProblemCreateInput;
import com.netflix.dgs.codegen.generated.types.ProblemResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import dev.poporo.course.ch04.exception.ProblemzAuthenticationException;
import dev.poporo.course.ch04.service.command.ProblemzCommandService;
import dev.poporo.course.ch04.service.query.ProblemzQueryService;
import dev.poporo.course.ch04.service.query.UserzQueryService;
import dev.poporo.course.ch04.util.GraphqlBeanMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

@DgsComponent
public class  ProblemDataResolver {

    @Autowired
    private ProblemzQueryService queryService;

    @Autowired
    private ProblemzCommandService commandService;

    @Autowired
    private UserzQueryService userzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList() {
        return queryService.problemzLatestList().stream().map(GraphqlBeanMapper::mapToGraphql)
                .toList();
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.ProblemDetail)
    public Problem  getProblemDetail(@InputArgument(name = "id") String problemId) {
        var problemzId = UUID.fromString(problemId);
        var problemz = queryService.problemzDetail(problemzId).get();

        return GraphqlBeanMapper.mapToGraphql(problemz);
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.ProblemCreate)
    public ProblemResponse createProblem(@RequestHeader(name = "authToken", required = true) String authToken,
                                         @InputArgument(name = "problem") ProblemCreateInput problemCreateInput) {
        var userz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);
        var problemz = GraphqlBeanMapper.mapToEntity(problemCreateInput, userz);
        var created = commandService.createProblemz(problemz);

        return ProblemResponse.newBuilder()
                .problem(GraphqlBeanMapper.mapToGraphql(created))
                .build();
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblemAdded() {
        return commandService.problemzFlux().map(GraphqlBeanMapper::mapToGraphql);
    }
}

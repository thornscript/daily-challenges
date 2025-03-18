package dev.poporo.course.ch04.util;

import com.netflix.dgs.codegen.generated.types.Problem;
import com.netflix.dgs.codegen.generated.types.ProblemCreateInput;
import com.netflix.dgs.codegen.generated.types.Solution;
import com.netflix.dgs.codegen.generated.types.SolutionCategory;
import com.netflix.dgs.codegen.generated.types.SolutionCreateInput;
import com.netflix.dgs.codegen.generated.types.User;
import com.netflix.dgs.codegen.generated.types.UserAuthToken;
import dev.poporo.course.ch04.datasource.problemz.entity.Problemz;
import dev.poporo.course.ch04.datasource.problemz.entity.Solutionz;
import dev.poporo.course.ch04.datasource.problemz.entity.Userz;
import dev.poporo.course.ch04.datasource.problemz.entity.UserzToken;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.ocpsoft.prettytime.PrettyTime;

public class GraphqlBeanMapper {

    private static final PrettyTime PRETTY_TIME = new PrettyTime();

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    public static User mapToGraphql(Userz original) {
        var result = new User();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);

        result.setAvatar(original.getAvatar());
        result.setEmail(original.getEmail());
        result.setCreateDateTime(createDateTime);
        result.setDisplayName(original.getDisplayName());
        result.setId(original.getId().toString());
        result.setUsername(original.getUsername());

        return result;
    }

    public static Problem mapToGraphql(Problemz original) {
        var result = new Problem();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var convertedSolutions = original.getSolutions().stream()
                .sorted(Comparator.comparing(Solutionz::getCreationTimestamp).reversed())
                .map(GraphqlBeanMapper::mapToGraphql)
                .toList();
        var tagList = List.of(original.getTags().split(","));

        result.setAuthor(author);
        result.setCreateDateTime(createDateTime);
        result.setSolutions(convertedSolutions);
        result.setTags(tagList);
        result.setTitle(original.getTitle());
        result.setId(original.getId().toString());
        result.setContent(original.getContent());
        result.setSolutionCount(original.getSolutions().size());
        result.setPrettyCreateDateTime(PRETTY_TIME.format(createDateTime));

        return result;
    }

    public static Solution mapToGraphql(Solutionz original) {
        var result = new Solution();
        var createDateTime = original.getCreationTimestamp().atOffset(ZONE_OFFSET);
        var author = mapToGraphql(original.getCreatedBy());
        var category = SolutionCategory.EXPLANATION.toString().equalsIgnoreCase(original.getCategory()) ?
                SolutionCategory.EXPLANATION : SolutionCategory.REFERENCE;

        result.setAuthor(author);
        result.setCategory(category);
        result.setCreateDateTime(createDateTime);
        result.setContent(original.getContent());
        result.setId(original.getId().toString());
        result.setVoteAsBadCount(original.getVoteBadCount());
        result.setVoteAsGoodCount(original.getVoteGoodCount());
        result.setPrettyCreateDateTime(PRETTY_TIME.format(createDateTime));

        return result;
    }

    public static UserAuthToken mapToGraphql(UserzToken original) {
        var result = new UserAuthToken();
        var expiryDateTime = original.getExpiryTimestamp().atOffset(ZONE_OFFSET);

        result.setAuthToken(original.getAuthToken());
        result.setExpiryTime(expiryDateTime);

        return result;
    }

    public static Problemz mapToEntity(ProblemCreateInput original, Userz author) {
        var result = new Problemz();

        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setId(UUID.randomUUID());
        result.setSolutions(Collections.emptyList());
        result.setTags(String.join(",", original.getTags()));
        result.setTitle(original.getTitle());

        return result;
    }

    public static Solutionz mapToEntity(SolutionCreateInput original, Userz author, Problemz problemz) {
        var result = new Solutionz();

        result.setCategory(original.getCategory().name());
        result.setContent(original.getContent());
        result.setCreatedBy(author);
        result.setId(UUID.randomUUID());
        result.setProblemz(problemz);

        return result;
    }
}

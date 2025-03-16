package dev.poporo.course.ch04.component.problemz;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.types.SearchItemFilter;
import com.netflix.dgs.codegen.generated.types.SearchableItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import dev.poporo.course.ch04.service.query.ProblemzQueryService;
import dev.poporo.course.ch04.service.query.SolutionzQueryService;
import dev.poporo.course.ch04.util.GraphqlBeanMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class ItemSearchDataResolver {

    @Autowired
    private ProblemzQueryService problemzQueryService;

    @Autowired
    private SolutionzQueryService solutionzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ItemSearch)
    public List<SearchableItem> searchItems(
            @InputArgument(name = "filter") SearchItemFilter filter) {
        var result = new ArrayList<SearchableItem>();
        var keyword = filter.getKeyword();

        var problemzByKeyword = problemzQueryService.problemzByKeyword(keyword)
                .stream().map(GraphqlBeanMapper::mapToGraphql).toList();
        result.addAll(problemzByKeyword);

        var solutionzByKeyword = solutionzQueryService.solutionzByKeyword(keyword)
                .stream().map(GraphqlBeanMapper::mapToGraphql).toList();
        result.addAll(solutionzByKeyword);

        if (result.isEmpty()) {
            throw new DgsEntityNotFoundException("No item with keyword " + keyword);
        }

        result.sort(Comparator.comparing(SearchableItem::getCreateDateTime).reversed());

        return result;
    }
}

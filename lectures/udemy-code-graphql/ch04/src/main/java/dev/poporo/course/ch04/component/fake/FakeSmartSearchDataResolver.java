package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.dgs.codegen.generated.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.poporo.course.ch04.datasource.fake.FakeBookDataSource;
import dev.poporo.course.ch04.datasource.fake.FakeHelloDataSource;
import dev.poporo.course.ch04.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartSearchDataResolver {

    @DgsQuery(field = "smartSearch")
    public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword")Optional<String> keyword) {
        var smartSearchList = new ArrayList<SmartSearchResult>();
        if (keyword.isEmpty()) {
            smartSearchList.addAll(FakeHelloDataSource.HELLO_LIST);
            smartSearchList.addAll(FakeBookDataSource.BOOK_LIST);
        } else {
            var keywordString = keyword.get();

            FakeHelloDataSource.HELLO_LIST.stream().filter(
                    h -> StringUtils.containsIgnoreCase(h.getText(), keywordString)
            ).forEach(smartSearchList::add);

            FakeBookDataSource.BOOK_LIST.stream().filter(
                    b -> StringUtils.containsIgnoreCase(b.getTitle(), keywordString)
            ).forEach(smartSearchList::add);
        }

        return smartSearchList;
    }
}

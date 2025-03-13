package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.dgs.codegen.generated.DgsConstants.RELEASEHISTORYINPUT;
import com.netflix.dgs.codegen.generated.types.Book;
import com.netflix.dgs.codegen.generated.types.ReleaseHistory;
import com.netflix.dgs.codegen.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.poporo.course.ch04.datasource.fake.FakeBookDataSource;
import graphql.schema.DataFetchingEnvironment;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DgsComponent
public class FakeBookDataResolver {

    @DgsQuery(field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {
        if (authorName.isEmpty() || StringUtils.isBlank(authorName.get())) {
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> b.getAuthor().getName().toLowerCase().contains(authorName.get().toLowerCase()))
                .toList();
    }

    @DgsQuery(field = QUERY.BooksByReleased)
    public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
        var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
        var releasedInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) releasedMap.get(RELEASEHISTORYINPUT.PrintedEdition))
                .year((int) releasedMap.get(RELEASEHISTORYINPUT.Year))
                .build();

        return FakeBookDataSource.BOOK_LIST.stream().filter(
                b -> this.matchReleaseHistory(releasedInput, b.getReleased())
        ).toList();
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element) {
        return input.getPrintedEdition().equals(element.getPrintedEdition())
                && input.getYear() == element.getYear();
    }
}

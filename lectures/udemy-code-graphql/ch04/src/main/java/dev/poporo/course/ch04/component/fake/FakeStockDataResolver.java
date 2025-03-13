package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.SUBSCRIPTION;
import com.netflix.dgs.codegen.generated.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsSubscription;
import dev.poporo.course.ch04.datasource.fake.FakeStockDataSource;
import java.time.Duration;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStockDataSource dataSource;

    // @DgsSubscription
    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = SUBSCRIPTION.RandomStock)
    public Publisher<Stock> randomStock() {
        return Flux.interval(Duration.ofSeconds(3)).map(t -> dataSource.randomStock());
    }
}

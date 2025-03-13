package dev.poporo.course.ch04.datasource.fake;

import com.netflix.dgs.codegen.generated.types.Stock;
import java.time.OffsetDateTime;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeStockDataSource {

    @Autowired
    private Faker faker;

    public Stock randomStock() {
        return Stock.newBuilder().lastTradeDateTime(OffsetDateTime.now())
                .price(faker.random().nextInt(100, 1000))
                .symbol(faker.stock().nyseSymbol())
                .build();
    }
}

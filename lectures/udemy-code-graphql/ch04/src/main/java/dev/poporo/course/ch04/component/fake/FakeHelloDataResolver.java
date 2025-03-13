package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import dev.poporo.course.ch04.datasource.fake.FakeHelloDataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos() {
        return FakeHelloDataSource.HELLO_LIST;
    }

    @DgsQuery
    public  Hello oneHello() {
        return FakeHelloDataSource.HELLO_LIST.get(
                ThreadLocalRandom.current().nextInt(FakeHelloDataSource.HELLO_LIST.size())
        );
    }
}

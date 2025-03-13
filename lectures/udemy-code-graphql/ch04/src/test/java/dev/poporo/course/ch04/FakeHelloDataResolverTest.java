package dev.poporo.course.ch04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FakeHelloDataResolverTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void testOneHello() {
        @Language("GraphQL") var graphqlQuery = """
                {
                    oneHello {
                        text
                        randomNumber
                    }
                }
                """;

        String text = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQuery, "data.oneHello.text");
        Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQuery, "data.oneHello.randomNumber");

        assertFalse(StringUtils.isBlank(text));
        assertNotNull(randomNumber);
    }

    @Test
    void testAllHellos() {
        @Language("GraphQL") var graphqlQuery = """
                {
                    allHellos {
                        text
                        randomNumber
                    }
                }
                """;

        List<String> texts = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQuery, "data.allHellos[*].text");
        List<Integer> randomNumbers = dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQuery, "data.allHellos[*].randomNumber");

        assertNotNull(texts);
        assertFalse(texts.isEmpty());
        assertNotNull(randomNumbers);
        assertEquals(texts.size(), randomNumbers.size());
    }
}

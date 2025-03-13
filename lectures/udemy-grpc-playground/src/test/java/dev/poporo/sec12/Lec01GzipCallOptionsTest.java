package dev.poporo.sec12;

import dev.poporo.models.sec12.BalanceCheckRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    It is a class to demo enabling gzip compression
    Update logback xml with DEBUG mode
 */
public class Lec01GzipCallOptionsTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01GzipCallOptionsTest.class);

    @Test
    public void gzipDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub
                .withCompression("gzip")
                .getAccountBalance(request);
        log.info("{}", response);
    }
}

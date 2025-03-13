package dev.poporo.sec11;

import dev.poporo.common.AbstractChannelTest;
import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec11.BalanceCheckRequest;
import dev.poporo.models.sec11.BankServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    It is a class to demo the eager channel creation behavior
    There is a bug: https://github.com/grpc/grpc-java/issues/10517
 */
public class Lec05EagerChannelDemoTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05EagerChannelDemoTest.class);

    @Test
    public void eagerChannelDemo() {
        log.info("{}", channel.getState(true));
    }
}

package dev.poporo.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.poporo.common.AbstractChannelTest;
import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec11.BalanceCheckRequest;
import dev.poporo.models.sec11.BankServiceGrpc;
import dev.poporo.models.sec11.WithdrawRequest;
import io.grpc.Deadline;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    It is a class to demo the lazy channel creation behavior
 */
public class Lec04LazyChannelDemoTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(Lec04LazyChannelDemoTest.class);
    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

    @BeforeAll
    public void setup() {
        // this.grpcServer.start();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

    @Test
    public void lazyChannelDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        // Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        // var response = this.bankBlockingStub.getAccountBalance(request);
        // log.info("{}", response);
    }
}

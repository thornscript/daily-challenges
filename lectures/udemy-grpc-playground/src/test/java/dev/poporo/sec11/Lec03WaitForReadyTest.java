package dev.poporo.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.poporo.common.AbstractChannelTest;
import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec11.BankServiceGrpc;
import dev.poporo.models.sec11.WithdrawRequest;
import io.grpc.Deadline;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03WaitForReadyTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03WaitForReadyTest.class);
    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

    @BeforeAll
    public void setup() {
        Runnable runnable = () -> {
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            this.grpcServer.start();
        };
        Thread.ofVirtual().start(runnable);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

    @Test
    public void blockingDeadlineTest() {
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();

        var iterator = this.bankBlockingStub.withWaitForReady()
                .withDeadline(Deadline.after(15, TimeUnit.SECONDS))
                .withdraw(request);

        while (iterator.hasNext()) {
            log.info("{}", iterator.next());
        }
    }
}

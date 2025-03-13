package dev.poporo.sec11;

import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec11.AccountBalance;
import dev.poporo.models.sec11.BalanceCheckRequest;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryDeadlineTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryDeadlineTest.class);

    @Test
    public void blockingDeadlineTest() {
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(1)
                    .build();

            var response = this.bankBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .getAccountBalance(request);
        });
        Assertions.assertEquals(Code.DEADLINE_EXCEEDED, ex.getStatus().getCode());
    }

    @Test
    public void asyncDeadlineTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();

        var observer = ResponseObserver.<AccountBalance>create();
        this.bankStub.withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                .getAccountBalance(request, observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertEquals(Code.DEADLINE_EXCEEDED, Status.fromThrowable(observer.getThrowable()).getCode());
    }
}

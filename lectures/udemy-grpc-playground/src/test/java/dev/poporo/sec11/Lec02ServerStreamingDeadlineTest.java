package dev.poporo.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.poporo.models.sec11.WithdrawRequest;
import io.grpc.Deadline;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec02ServerStreamingDeadlineTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ServerStreamingDeadlineTest.class);

    @Test
    public void blockingDeadlineTest() {
        try {
            var request = WithdrawRequest.newBuilder()
                    .setAccountNumber(1)
                    .setAmount(50)
                    .build();

            var iterator = this.bankBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .withdraw(request);

            while (iterator.hasNext()) {
                log.info("{}", iterator.next());
            }
        } catch (Exception e) {
            log.info("error");
        }

        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
    }

}

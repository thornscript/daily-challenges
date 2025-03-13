package dev.poporo.sec09;

import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec09.AccountBalance;
import dev.poporo.models.sec09.BalanceCheckRequest;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryInputValidationTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryInputValidationTest.class);

    @Test
    public void blockingInputValidationTest() {
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(11)
                    .build();
            var response = this.bankBlockingStub.getAccountBalance(request);
        });
        Assertions.assertEquals(Code.INVALID_ARGUMENT, ex.getStatus().getCode());
    }

    @Test
    public void asyncInputValidationTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(11)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.bankStub.getAccountBalance(request, observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(Code.INVALID_ARGUMENT, ((StatusRuntimeException) observer.getThrowable()).getStatus().getCode());
    }
}

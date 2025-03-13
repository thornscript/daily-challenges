package dev.poporo.sec09;

import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec09.AccountBalance;
import dev.poporo.models.sec09.Money;
import dev.poporo.models.sec09.WithdrawRequest;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Lec02ServerStreamingInputValidationTest extends AbstractTest {

    @ParameterizedTest
    @MethodSource("testdata")
    public void blockingInputValidationTest(WithdrawRequest request, Status.Code code) {
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var response = this.bankBlockingStub.withdraw(request).hasNext();
        });

        Assertions.assertEquals(code, ex.getStatus().getCode());
    }

    @ParameterizedTest
    @MethodSource("testdata")
    public void asyncInputValidationTest(WithdrawRequest request, Status.Code code) {
        var observer = ResponseObserver.<Money>create();
        this.bankStub.withdraw(request, observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(code, ((StatusRuntimeException) observer.getThrowable()).getStatus().getCode());
    }

    private Stream<Arguments> testdata() {
        return Stream.of(
                Arguments.of(WithdrawRequest.newBuilder().setAccountNumber(11).setAmount(10).build(), Code.INVALID_ARGUMENT),
                Arguments.of(WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(17).build(), Code.INVALID_ARGUMENT),
                Arguments.of(WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(120).build(), Code.FAILED_PRECONDITION)
        );
    }
}

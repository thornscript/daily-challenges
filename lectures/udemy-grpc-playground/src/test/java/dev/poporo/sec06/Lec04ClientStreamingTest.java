package dev.poporo.sec06;

import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec06.AccountBalance;
import dev.poporo.models.sec06.DepositRequest;
import dev.poporo.models.sec06.Money;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Lec04ClientStreamingTest extends AbstractTest {

    @Test
    public void depositTest() {
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver =  this.bankStub.deposit(responseObserver);

        // initial message - account number
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());
        // Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        // requestObserver.onError(new RuntimeException());

        // sending stream of money
        IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
                .forEach(requestObserver::onNext);

        // notifying the server that we are done
        requestObserver.onCompleted();

        // at this point out response observer should receive a response
        responseObserver.await();

        Assertions.assertEquals(1, responseObserver.getItems().size());
        Assertions.assertEquals(200, responseObserver.getItems().getFirst().getBalance());
        Assertions.assertNull(responseObserver.getThrowable());
    }
}

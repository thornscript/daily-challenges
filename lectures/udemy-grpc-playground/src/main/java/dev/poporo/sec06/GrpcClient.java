package dev.poporo.sec06;

import dev.poporo.models.sec06.AccountBalance;
import dev.poporo.models.sec06.BalanceCheckRequest;
import dev.poporo.models.sec06.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcClient {

    private static final Logger log = LoggerFactory.getLogger(GrpcClient.class);

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        var stub = BankServiceGrpc.newStub(channel);

        stub.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build(),
                new StreamObserver<>() {
                    @Override
                    public void onNext(AccountBalance value) {
                        log.info("Received account balance: {}", value);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onCompleted() {
                        log.info("Balance check completed");
                    }
                });

        log.info("Balance check completed");
        Thread.sleep(Duration.ofSeconds(1));
    }
}

package dev.poporo.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.poporo.models.sec11.AccountBalance;
import dev.poporo.models.sec11.BalanceCheckRequest;
import dev.poporo.models.sec11.BankServiceGrpc;
import dev.poporo.models.sec11.Money;
import dev.poporo.models.sec11.WithdrawRequest;
import dev.poporo.sec11.repository.AccountRepository;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadlineBankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(DeadlineBankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount();
        var accountBalance = AccountRepository.getBalance(accountNumber);

        if (requestedAmount > accountBalance) {
            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException());
            return;
        }

        for (int i = 0; i < (requestedAmount / 10) && !Context.current().isCancelled(); i++) {
            var money = Money.newBuilder()
                    .setAmount(10)
                    .build();
            responseObserver.onNext(money);
            log.info("money sent {}", money);
            AccountRepository.deductAmount(accountNumber, 10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        log.info("streaming completed");
        responseObserver.onCompleted();
    }
}

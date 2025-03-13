package dev.poporo.sec09;

import com.google.common.util.concurrent.Uninterruptibles;
import dev.poporo.models.sec09.AccountBalance;
import dev.poporo.models.sec09.BalanceCheckRequest;
import dev.poporo.models.sec09.BankServiceGrpc;
import dev.poporo.models.sec09.Money;
import dev.poporo.models.sec09.WithdrawRequest;
import dev.poporo.sec09.repository.AccountRepository;
import dev.poporo.sec09.validator.RequestValidator;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        RequestValidator.validateAccount(request.getAccountNumber())
                .map(Status::asRuntimeException)
                .ifPresentOrElse(
                        responseObserver::onError,
                        () -> sendAccountBalance(request, responseObserver)
                );
    }

    private void sendAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        RequestValidator.validateAccount(request.getAccountNumber())
                .or(() -> RequestValidator.isAmountDivisibleBy10(request.getAmount()))
                .or(() -> RequestValidator.hasSufficientBalance(request.getAmount(), AccountRepository.getBalance(request.getAccountNumber())))
                .map(Status::asRuntimeException)
                .ifPresentOrElse(
                        responseObserver::onError,
                        () -> sendMoney(request, responseObserver)
                );
    }

    private void sendMoney(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        try {
            var accountNumber = request.getAccountNumber();
            var requestedAmount = request.getAmount();

            for (int i = 0; i < (requestedAmount / 10); i++) {
                var money = Money.newBuilder()
                        .setAmount(10)
                        .build();
                if (i == 3) {
                    throw new RuntimeException("oops");
                }
                responseObserver.onNext(money);
                log.info("money sent {}", money);
                AccountRepository.deductAmount(accountNumber, 10);
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException()
            );
        }
    }
}

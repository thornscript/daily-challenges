package dev.poporo.sec06;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import dev.poporo.models.sec06.AccountBalance;
import dev.poporo.models.sec06.AllAccountsResponse;
import dev.poporo.models.sec06.BalanceCheckRequest;
import dev.poporo.models.sec06.BankServiceGrpc;
import dev.poporo.models.sec06.DepositRequest;
import dev.poporo.models.sec06.Money;
import dev.poporo.models.sec06.WithdrawRequest;
import dev.poporo.sec06.repository.AccountRepository;
import dev.poporo.sec06.requesthandlers.DepositRequestHandler;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
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
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
        var accounts = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(e -> AccountBalance.newBuilder().setAccountNumber(e.getKey()).setBalance(e.getValue()).build())
                .toList();
        var response = AllAccountsResponse.newBuilder()
                .addAllAccounts(accounts)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        /*
            Ideally we should do some input validation, But we are going to assume only happy path scenarios.
            Because, in gRPC, there are multiple ways to communicate the error message to the client. It was to be
            discussed in detail separately.
            Assumption: account # 1 - 10 & withdraw amount is multiple of $10
         */
        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount();
        var accountBalance = AccountRepository.getBalance(accountNumber);

        if (requestedAmount > accountBalance) {
            responseObserver.onCompleted(); // we will change it to proper error later
            return;
        }

        for (int i = 0; i < (requestedAmount / 10); i++) {
            var money = Money.newBuilder()
                    .setAmount(10)
                    .build();
            responseObserver.onNext(money);
            log.info("money sent {}", money);
            AccountRepository.deductAmount(accountNumber, 10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> deposit(StreamObserver<AccountBalance> responseObserver) {
        return new DepositRequestHandler(responseObserver);
    }
}

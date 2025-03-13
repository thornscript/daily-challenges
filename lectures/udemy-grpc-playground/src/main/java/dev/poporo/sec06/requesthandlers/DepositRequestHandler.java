package dev.poporo.sec06.requesthandlers;

import dev.poporo.models.sec06.AccountBalance;
import dev.poporo.models.sec06.DepositRequest;
import dev.poporo.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepositRequestHandler implements StreamObserver<DepositRequest> {

    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);
    private final StreamObserver<AccountBalance> responseObserver;
    private int accountNumber;

    public DepositRequestHandler(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        switch (depositRequest.getRequestCase()) {
            case ACCOUNT_NUMBER -> this.accountNumber = depositRequest.getAccountNumber();
            case MONEY -> AccountRepository.addAmount(this.accountNumber, depositRequest.getMoney().getAmount());
        }
    }

    @Override
    public void onError(Throwable t) {
        log.info("client error {}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();
        this.responseObserver.onNext(accountBalance);
        this.responseObserver.onCompleted();
    }
}

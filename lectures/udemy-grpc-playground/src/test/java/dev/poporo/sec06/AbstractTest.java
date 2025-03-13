package dev.poporo.sec06;

import dev.poporo.common.AbstractChannelTest;
import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec06.BankServiceGrpc;
import dev.poporo.models.sec06.TransferServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new BankService(), new TransferService());

    protected BankServiceGrpc.BankServiceStub bankStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected TransferServiceGrpc.TransferServiceStub transferStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.transferStub = TransferServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }
}

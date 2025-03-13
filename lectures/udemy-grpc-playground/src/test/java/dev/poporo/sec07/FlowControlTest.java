package dev.poporo.sec07;

import dev.poporo.common.AbstractChannelTest;
import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec07.FlowControlServiceGrpc;
import dev.poporo.models.sec07.FlowControlServiceGrpc.FlowControlServiceStub;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class FlowControlTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(new FlowControlService());
    private FlowControlServiceStub stub;

    @BeforeAll
    public void setup() {
        this.server.start();
        this.stub = FlowControlServiceGrpc.newStub(channel);
    }

    @Test
    public void flowControlDemo() {
        var responseObserver = new ResponseHandler();
        var requestObserver = this.stub.getMessages(responseObserver);
        responseObserver.setRequestObserver(requestObserver);
        // responseObserver.start();
        // responseObserver.await();
    }

    @AfterAll
    public void stop() {
        this.server.stop();
    }
}

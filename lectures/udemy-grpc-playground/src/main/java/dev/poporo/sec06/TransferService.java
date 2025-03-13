package dev.poporo.sec06;

import dev.poporo.models.sec06.TransferRequest;
import dev.poporo.models.sec06.TransferResponse;
import dev.poporo.models.sec06.TransferServiceGrpc.TransferServiceImplBase;
import dev.poporo.sec06.requesthandlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}

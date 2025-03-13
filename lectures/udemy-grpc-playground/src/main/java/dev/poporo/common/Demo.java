package dev.poporo.common;

import dev.poporo.sec06.BankService;
import dev.poporo.sec06.TransferService;

public class Demo {

    public static void main(String[] args) {

        GrpcServer.create(new BankService(), new TransferService())
                .start()
                .await();
    }
}

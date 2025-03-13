package dev.poporo.sec12;

import dev.poporo.models.sec12.BalanceCheckRequest;
import dev.poporo.sec12.interceptors.GzipRequestInterceptor;
import io.grpc.ClientInterceptor;
import java.util.List;
import org.junit.jupiter.api.Test;

/*
    It is a class to demo enabling gzip compression via interceptor
    Update logback xml with DEBUG mode
 */
public class Lec04GzipInterceptorTest extends AbstractInterceptorTest {

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new GzipRequestInterceptor());
    }

    @Test
    void gzipInterceptorDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);
    }
}

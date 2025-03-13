package dev.poporo.sec12;

import static dev.poporo.sec12.Constants.API_KEY;

import dev.poporo.common.GrpcServer;
import dev.poporo.models.sec12.BalanceCheckRequest;
import dev.poporo.sec12.interceptors.ApiKeyValidationInterceptor;
import dev.poporo.sec12.interceptors.GzipResponseInterceptor;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    It is a class to demo how to attach metadata for every request via interceptor
 */
public class Lec05ClientApiKeyInterceptorTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec05ClientApiKeyInterceptorTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(
                MetadataUtils.newAttachHeadersInterceptor(getApiKey())
        );
    }

    @Override
    protected GrpcServer createServer() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        });
    }

    @Test
    public void clientApiKeyDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);
        log.info("{}", response);
    }

    private Metadata getApiKey() {
        var metadata = new Metadata();
        metadata.put(API_KEY, "bank-client-secret");
        return metadata;
    }
}

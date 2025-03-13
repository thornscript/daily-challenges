package dev.poporo.sec12;

import dev.poporo.common.GrpcServer;
import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec12.BalanceCheckRequest;
import dev.poporo.models.sec12.Money;
import dev.poporo.models.sec12.WithdrawRequest;
import dev.poporo.sec12.interceptors.ApiKeyValidationInterceptor;
import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06UserSessionTokenInterceptorTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec06UserSessionTokenInterceptorTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return Collections.emptyList();
    }

    @Override
    protected GrpcServer createServer() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        });
    }

    @Test
    public void userCredentialsDemo() {
        for (int i = 1; i <= 5; ++i) {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(1)
                    .build();
            var response = this.bankBlockingStub
                    .withCallCredentials(new UserSessionToken("user-token-" + i))
                    .getAccountBalance(request);
            log.info("{}", response);
        }
    }

    @Test
    public void streamingUserCredentialsDemo() {
        for (int i = 1; i <= 5; ++i) {
            var observer = ResponseObserver.<Money>create();
            var request = WithdrawRequest.newBuilder()
                    .setAccountNumber(i)
                    .setAmount(30)
                    .build();
            this.bankStub.withdraw(request, observer);
            observer.await();
        }
    }

    private static class UserSessionToken extends CallCredentials {

        private static final String TOKEN_FORMAT = "%s %s";
        private final String jwt;

        private UserSessionToken(String jwt) {
            this.jwt = jwt;
        }

        @Override
        public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
            appExecutor.execute(() -> {
                var metadata = new Metadata();
                metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
            });
        }
    }
}

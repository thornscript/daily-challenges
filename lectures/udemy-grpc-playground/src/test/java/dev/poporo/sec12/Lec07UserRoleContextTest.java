package dev.poporo.sec12;

import dev.poporo.common.GrpcServer;
import dev.poporo.common.ResponseObserver;
import dev.poporo.models.sec12.BalanceCheckRequest;
import dev.poporo.models.sec12.Money;
import dev.poporo.models.sec12.WithdrawRequest;
import dev.poporo.sec12.interceptors.UserRoleInterceptor;
import dev.poporo.sec12.interceptors.UserTokenInterceptor;
import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07UserRoleContextTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec07UserRoleContextTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return Collections.emptyList();
    }

    @Override
    protected GrpcServer createServer() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new UserRoleBankService())
                    .intercept(new UserRoleInterceptor());
        });
    }

    @RepeatedTest(5)
    public void unaryUserCredentialsDemo() {
        for (int i = 1; i <= 5; ++i) {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(i)
                    .build();
            var response = this.bankBlockingStub
                    .withCallCredentials(new UserSessionToken("user-token-" + i))
                    .getAccountBalance(request);
            log.info("{}", response);
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

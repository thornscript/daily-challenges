package dev.poporo.sec12.interceptors;

import dev.poporo.sec12.Constants;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiKeyValidationInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiKeyValidationInterceptor.class);

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                      ServerCallHandler<ReqT, RespT> next) {
        log.info("{}", call.getMethodDescriptor().getFullMethodName());
        var apiKey = headers.get(Constants.API_KEY);
        if (isValid(apiKey)) {
            return next.startCall(call, headers);
        }
        call.close(
                Status.UNAUTHENTICATED.withDescription("client must provide valid api key"),
                headers
        );
        return new ServerCall.Listener<>() {};
    }

    private boolean isValid(String apiKey) {
        return Objects.nonNull(apiKey) && apiKey.equals("bank-client-secret");
    }
}

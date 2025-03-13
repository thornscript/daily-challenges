package dev.poporo.sec12.interceptors;

import dev.poporo.sec12.Constants;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    user-token-1, user-token-2  => prime users, all calls are allowed
    user-token-3, user-token-4  => standard users, server streaming calls are NOT allowed. other calls are allowed.
    any other token             => not valid...!
 */
public class UserTokenInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserTokenInterceptor.class);
    private static final Set<String> PRIME_SET = Set.of("user-token-1", "user-token-2");
    private static final Set<String> STANDARD_SET = Set.of("user-token-3", "user-token-4");

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                      ServerCallHandler<ReqT, RespT> next) {
        var token = extractToken(headers.get(Constants.USER_TOKEN_KEY));
        log.info("{}", token);
        if (isValid(token)) {
            return close(call, headers, Status.UNAUTHENTICATED.withDescription("token is either null or invalid"));
        }
        var isOneMessage = call.getMethodDescriptor().getType().serverSendsOneMessage();
        if (isOneMessage || PRIME_SET.contains(token)) {
            return next.startCall(call, headers);
        }
        return close(call, headers, Status.PERMISSION_DENIED);
    }

    private String extractToken(String value) {
        return Objects.nonNull(value) && value.startsWith(Constants.BEARER) ?
                value.substring(Constants.BEARER.length()).trim() : null;
    }

    private boolean isValid(String token) {
        return Objects.nonNull(token) && (PRIME_SET.contains(token) || STANDARD_SET.contains(token));
    }

    private <ReqT, RespT> Listener<ReqT> close(ServerCall<ReqT, RespT> call, Metadata metadata, Status status) {
        call.close(status, metadata);
        return new Listener<ReqT>() {
        };
    }
}

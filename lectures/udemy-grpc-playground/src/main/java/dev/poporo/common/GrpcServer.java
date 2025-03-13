package dev.poporo.common;

import dev.poporo.sec12.interceptors.GzipResponseInterceptor;
import io.grpc.BindableService;
import io.grpc.Codec.Gzip;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer {

    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    private final Server server;

    private GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer create(int port, BindableService... services) {
        return create(port, builder -> {
           Arrays.asList(services).forEach(builder::addService);
        });
    }

    public static GrpcServer create(BindableService... services) {
        return create(6565, services);
    }

    public static GrpcServer create(int port, Consumer<ServerBuilder<?>> consumer) {
        var builder = ServerBuilder.forPort(port);
        consumer.accept(builder);
        return new GrpcServer(builder.build());
    }

    public GrpcServer start() {
        var services = server.getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();
        try {
            server.start();
            log.info("Server started on port {}, services: {}", server.getPort(), services);
            return this;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to start grpc server", ex);
        }
    }

    public void await() {
        try {
            server.awaitTermination();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to stop grpc server", ex);
        }
    }

    public void stop() {
        server.shutdownNow();
        log.info("Server stopped");
    }
}


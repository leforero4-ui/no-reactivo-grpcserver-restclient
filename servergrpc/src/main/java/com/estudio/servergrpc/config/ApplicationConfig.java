package com.estudio.servergrpc.config;

import com.estudio.servergrpc.controller.BookServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    
    @Autowired
    BookServiceGrpc bookServiceGrpc;
    
    @Value("${grpc.server.port}")
    Integer port;
    
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            Server server = ServerBuilder
                    .forPort(this.port)
                    .addService(this.bookServiceGrpc)
                    .addService(ProtoReflectionService.newInstance()) //activa la reflección para que no tenga necesidad de que el cliente tenga los protos, aunque lo mas recomendable es que los tenga por seguridad
                    .build();

            server.start();

            this.logger.info("ServerGrpc started on port(s): {} (http2)", this.port);
            
            server.awaitTermination();
        };
    }
}

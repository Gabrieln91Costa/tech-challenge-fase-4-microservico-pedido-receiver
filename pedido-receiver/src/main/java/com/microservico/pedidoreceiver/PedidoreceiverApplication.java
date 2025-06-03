package com.microservico.pedidoreceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.microservico.pedidoreceiver.domain.repository") // <- Corrigido
@OpenAPIDefinition(
    info = @Info(
        title = "Microserviço PedidoReceiver",
        description = "API para recebimento e enfileiramento de pedidos",
        version = "1.0"
    )
)
@SecurityScheme(
    name = "jwt_auth",
    scheme = "bearer",
    bearerFormat = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class PedidoreceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidoreceiverApplication.class, args);
    }
}

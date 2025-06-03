package com.microservico.pedidoreceiver.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfig {

    @Bean
    public Queue filaPedidos() {
        return new Queue("fila.pedidos", true); // true = durável
    }
}

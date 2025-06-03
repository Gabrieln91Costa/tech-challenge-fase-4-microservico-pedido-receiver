package com.microservico.pedidoreceiver.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${pedido.exchange.name:pedido.exchange}")
    private String exchange;

    @Value("${pedido.routing.key:pedido.routingkey}")
    private String routingKey;

    public PedidoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarPedido(Pedido pedido) {
        // Envia direto para a fila usando default exchange
        rabbitTemplate.convertAndSend("", "fila.pedidos", pedido);

        try {
            String pedidoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedido);
            System.out.println("✅ ETAPA 05 - Pedido ENIVIADO PARA FILA");
            System.out.println(pedidoJson);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter pedido para JSON: " + e.getMessage());
        }
    }
}

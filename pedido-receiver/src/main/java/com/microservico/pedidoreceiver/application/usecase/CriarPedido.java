package com.microservico.pedidoreceiver.application.usecase;

import com.microservico.pedidoreceiver.domain.model.Pedido;

public interface CriarPedido {
    void executar(Pedido pedido);
}

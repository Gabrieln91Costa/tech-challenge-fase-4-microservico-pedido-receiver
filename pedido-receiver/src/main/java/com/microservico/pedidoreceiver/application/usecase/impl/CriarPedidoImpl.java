package com.microservico.pedidoreceiver.application.usecase.impl;

import com.microservico.pedidoreceiver.application.usecase.CriarPedido;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarPedidoImpl implements CriarPedido {

    private final PedidoMongoRepository pedidoRepository;

    @Override
    public void executar(Pedido pedido) {
        pedidoRepository.save(pedido);
    }
}

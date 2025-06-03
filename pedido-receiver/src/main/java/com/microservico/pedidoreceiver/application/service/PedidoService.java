package com.microservico.pedidoreceiver.application.service;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoMongoRepository pedidoRepository;

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> porId(String id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
}

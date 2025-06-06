package com.microservico.pedidoreceiver.domain.repository;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.model.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class PedidoMongoRepositoryTest {

    @Autowired
    private PedidoMongoRepository pedidoRepository;

    @BeforeEach
    void limparBanco() {
        pedidoRepository.deleteAll(); // garante base limpa
    }

    @Test
    @DisplayName("Deve salvar e buscar Pedido por ID")
    void deveSalvarEBuscarPorId() {
        Pedido pedido = new Pedido();
        pedido.setCpfCliente("12345678900");
        pedido.setNumeroCartao("1111-2222-3333-4444");
        pedido.setValorTotal(199.90);
        pedido.setStatus(StatusPedido.ABERTO);

        Pedido salvo = pedidoRepository.save(pedido);

        Optional<Pedido> encontrado = pedidoRepository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCpfCliente()).isEqualTo("12345678900");
        assertThat(encontrado.get().getStatus()).isEqualTo(StatusPedido.ABERTO);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar ID inexistente")
    void deveRetornarVazioParaIdInexistente() {
        Optional<Pedido> resultado = pedidoRepository.findById("id-inexistente");
        assertThat(resultado).isNotPresent();
    }
}

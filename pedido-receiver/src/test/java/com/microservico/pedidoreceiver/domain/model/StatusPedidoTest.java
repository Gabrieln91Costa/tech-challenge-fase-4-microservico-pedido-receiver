package com.microservico.pedidoreceiver.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPedidoTest {

    @Test
    void deveConterTodosOsStatusEsperados() {
        StatusPedido[] valores = StatusPedido.values();

        assertEquals(5, valores.length);
        assertArrayEquals(
            new StatusPedido[] {
                StatusPedido.ABERTO,
                StatusPedido.FECHADO_COM_SUCESSO,
                StatusPedido.FECHADO_SEM_CREDITO,
                StatusPedido.FECHADO_SEM_ESTOQUE,
                StatusPedido.CANCELADO
            },
            valores
        );
    }

    @Test
    void deveRetornarEnumCorretoPeloNome() {
        assertEquals(StatusPedido.ABERTO, StatusPedido.valueOf("ABERTO"));
        assertEquals(StatusPedido.CANCELADO, StatusPedido.valueOf("CANCELADO"));
    }

    @Test
    void deveLancarExcecaoParaNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            StatusPedido.valueOf("INEXISTENTE");
        });
    }
}

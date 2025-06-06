package com.microservico.pedidoreceiver.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        String id = "pedido123";
        String mensagemEsperada = "Pedido com ID " + id + " não encontrado";

        PedidoNaoEncontradoException exception = new PedidoNaoEncontradoException(id);

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}

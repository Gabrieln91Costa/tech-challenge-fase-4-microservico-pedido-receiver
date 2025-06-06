package com.microservico.pedidoreceiver.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        String cpf = "12345678900";
        String mensagemEsperada = "Cliente não encontrado: " + cpf;

        ClienteNaoEncontradoException exception = new ClienteNaoEncontradoException(mensagemEsperada);

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}

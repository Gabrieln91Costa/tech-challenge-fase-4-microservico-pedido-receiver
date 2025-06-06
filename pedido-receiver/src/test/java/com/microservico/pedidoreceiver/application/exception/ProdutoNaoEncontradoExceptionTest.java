package com.microservico.pedidoreceiver.application.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoNaoEncontradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        String sku = "SKU123";
        String mensagemEsperada = "Produto com SKU " + sku + " não encontrado";

        ProdutoNaoEncontradoException exception = new ProdutoNaoEncontradoException(sku);

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}

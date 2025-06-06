package com.microservico.pedidoreceiver.application.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String produtoSku) {
        super("Produto com SKU " + produtoSku + " não encontrado");
    }
}

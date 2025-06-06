package com.microservico.pedidoreceiver.application.exception;

/**
 * Exceção que indica que o pedido com o ID informado não foi encontrado.
 */
public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(String id) {
        super("Pedido com ID " + id + " não encontrado");
    }
}

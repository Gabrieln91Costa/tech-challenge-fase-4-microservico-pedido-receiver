package com.microservico.pedidoreceiver.application.usecase;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.application.exception.ClienteNaoEncontradoException;
import com.microservico.pedidoreceiver.application.exception.ProdutoNaoEncontradoException;

/**
 * Interface para o caso de uso de criar pedido.
 * Lança exceções quando o cliente ou produto não são encontrados.
 */
public interface CriarPedido {

    /**
     * Executa o processo de criação de um pedido.
     * 
     * @param pedido O pedido a ser criado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws ProdutoNaoEncontradoException Se algum produto não for encontrado
     */
    void executar(Pedido pedido) throws ClienteNaoEncontradoException, ProdutoNaoEncontradoException;
}

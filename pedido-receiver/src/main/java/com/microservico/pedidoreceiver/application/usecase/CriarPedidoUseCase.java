package com.microservico.pedidoreceiver.application.usecase;

import com.microservico.pedidoreceiver.domain.model.ItemPedido;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.model.StatusPedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import com.microservico.pedidoreceiver.application.exception.ClienteNaoEncontradoException;
import com.microservico.pedidoreceiver.application.exception.ProdutoNaoEncontradoException;
import com.microservico.pedidoreceiver.infrastructure.messaging.PedidoProducer;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase implements CriarPedido {

    private final PedidoMongoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;
    private final RestTemplate restTemplate;

    // URLs ajustadas para configuração externa idealmente
    private final String clienteServiceUrl = "http://cliente.service/api/clientes/";
    private final String produtoServiceUrl = "http://produto.service/api/produtos/";

    @Override
    public void executar(Pedido pedido) {
        validarCliente(pedido.getCpfCliente());
        validarProdutos(pedido);

        pedido.setStatus(StatusPedido.ABERTO);
        pedidoRepository.save(pedido);
        pedidoProducer.enviarPedido(pedido);
    }

    private void validarCliente(String cpf) {
        try {
            restTemplate.getForEntity(clienteServiceUrl + cpf, Void.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ClienteNaoEncontradoException("Cliente com CPF " + cpf + " não encontrado");
            }
            throw new IllegalStateException("Erro ao verificar CPF do cliente: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao verificar CPF do cliente: " + e.getMessage(), e);
        }
    }

    private void validarProdutos(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            try {
                restTemplate.getForEntity(produtoServiceUrl + item.getSku(), Void.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new ProdutoNaoEncontradoException("Produto com SKU " + item.getSku() + " não encontrado");
                }
                throw new IllegalStateException("Erro ao verificar SKU do produto: " + item.getSku() + ". Detalhes: " + e.getMessage(), e);
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao verificar SKU do produto: " + item.getSku() + ". Detalhes: " + e.getMessage(), e);
            }
        }
    }
}

package com.microservico.pedidoreceiver.application.usecase;

import com.microservico.pedidoreceiver.domain.model.ItemPedido;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.model.StatusPedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import com.microservico.pedidoreceiver.infrastructure.messaging.PedidoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase implements CriarPedido {

    private final PedidoMongoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;
    private final RestTemplate restTemplate;

    private final String clienteServiceUrl = "http://clienteservice:8080/clientes/";
    private final String produtoServiceUrl = "http://produtoservice:8080/produtos/sku/";

    @Override
    public void executar(Pedido pedido) {
        validarCliente(pedido.getCpfCliente());
        validarProdutos(pedido); // se algum SKU falhar, para aqui

        pedido.setStatus(StatusPedido.ABERTO);
        Pedido salvo = pedidoRepository.save(pedido);
        pedidoProducer.enviarPedido(salvo);
    }

    private void validarCliente(String cpf) {
        try {
            restTemplate.getForEntity(clienteServiceUrl + cpf, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("❌ Cliente com CPF " + cpf + " não encontrado. Verifique e tente novamente.");
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao verificar CPF do cliente: " + e.getMessage());
        }
    }

    private void validarProdutos(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            try {
                restTemplate.getForEntity(produtoServiceUrl + item.getSku(), Void.class);
            } catch (HttpClientErrorException.NotFound e) {
                throw new IllegalArgumentException("❌ Produto com SKU " + item.getSku() + " não encontrado. Verifique e tente novamente.");
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao verificar SKU do produto: " + item.getSku() + ". Detalhes: " + e.getMessage());
            }
        }
    }
}

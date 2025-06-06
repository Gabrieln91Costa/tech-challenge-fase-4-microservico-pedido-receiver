package com.microservico.pedidoreceiver.domain.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void deveSetarEAcessarTodosOsCamposCorretamente() {
        Pedido pedido = new Pedido();

        String id = "pedido123";
        String cpf = "12345678900";
        String cartao = "1111-2222-3333-4444";
        Double valor = 150.75;
        StatusPedido status = StatusPedido.ABERTO;

        ItemPedido item1 = new ItemPedido();
        item1.setSku("sku1");
        item1.setQuantidade(2);
        item1.setPrecoUnitario(50.0);

        ItemPedido item2 = new ItemPedido();
        item2.setSku("sku2");
        item2.setQuantidade(1);
        item2.setPrecoUnitario(50.75);

        List<ItemPedido> itens = Arrays.asList(item1, item2);

        pedido.setId(id);
        pedido.setCpfCliente(cpf);
        pedido.setNumeroCartao(cartao);
        pedido.setValorTotal(valor);
        pedido.setStatus(status);
        pedido.setItens(itens);

        assertEquals(id, pedido.getId());
        assertEquals(cpf, pedido.getCpfCliente());
        assertEquals(cartao, pedido.getNumeroCartao());
        assertEquals(valor, pedido.getValorTotal());
        assertEquals(status, pedido.getStatus());
        assertEquals(2, pedido.getItens().size());
        assertEquals("sku1", pedido.getItens().get(0).getSku());
    }

    @Test
    void construtorPadraoDeveCriarObjetoVazio() {
        Pedido pedido = new Pedido();

        assertNotNull(pedido);
        assertNull(pedido.getId());
        assertNull(pedido.getCpfCliente());
        assertNull(pedido.getNumeroCartao());
        assertNull(pedido.getValorTotal());
        assertNull(pedido.getStatus());
        assertNull(pedido.getItens());
    }
}

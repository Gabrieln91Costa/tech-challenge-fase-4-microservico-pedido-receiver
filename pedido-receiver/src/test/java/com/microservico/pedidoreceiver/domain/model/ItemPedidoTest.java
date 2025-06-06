package com.microservico.pedidoreceiver.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    @Test
    void deveSetarEAcessarAtributosCorretamente() {
        ItemPedido item = new ItemPedido();

        item.setSku("sku123");
        item.setQuantidade(3);
        item.setPrecoUnitario(29.99);

        assertEquals("sku123", item.getSku());
        assertEquals(3, item.getQuantidade());
        assertEquals(29.99, item.getPrecoUnitario());
    }

    @Test
    void construtorPadraoDevePermitirCriacaoDeObjeto() {
        ItemPedido item = new ItemPedido();

        assertNotNull(item);
        assertNull(item.getSku());
        assertNull(item.getQuantidade());
        assertNull(item.getPrecoUnitario());
    }
}

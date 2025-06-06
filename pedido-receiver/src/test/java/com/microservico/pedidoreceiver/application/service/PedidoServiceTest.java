package com.microservico.pedidoreceiver.application.service;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoMongoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId("pedido123");
        // Adicione outros atributos se necessário
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.salvar(pedido);

        assertNotNull(resultado);
        assertEquals("pedido123", resultado.getId());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void deveBuscarPedidoPorId() {
        when(pedidoRepository.findById("pedido123")).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.porId("pedido123");

        assertTrue(resultado.isPresent());
        assertEquals("pedido123", resultado.get().getId());
        verify(pedidoRepository).findById("pedido123");
    }

    @Test
    void deveRetornarOptionalVazioSePedidoNaoForEncontrado() {
        when(pedidoRepository.findById("inexistente")).thenReturn(Optional.empty());

        Optional<Pedido> resultado = pedidoService.porId("inexistente");

        assertFalse(resultado.isPresent());
        verify(pedidoRepository).findById("inexistente");
    }

    @Test
    void deveListarTodosPedidos() {
        Pedido outro = new Pedido();
        outro.setId("pedido456");

        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido, outro));

        List<Pedido> resultado = pedidoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("pedido123", resultado.get(0).getId());
        assertEquals("pedido456", resultado.get(1).getId());
        verify(pedidoRepository).findAll();
    }
}

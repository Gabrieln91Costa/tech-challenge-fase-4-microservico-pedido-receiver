package com.microservico.pedidoreceiver.infrastructure.controller;

import com.microservico.pedidoreceiver.application.usecase.CriarPedidoUseCase;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    private CriarPedidoUseCase criarPedidoUseCase;
    private PedidoController pedidoController;

    @BeforeEach
    void setup() {
        criarPedidoUseCase = mock(CriarPedidoUseCase.class);
        pedidoController = new PedidoController(criarPedidoUseCase);
    }

    @Test
    @DisplayName("Deve criar pedido com sucesso e retornar status 200")
    void deveCriarPedidoComSucesso() {
        Pedido pedido = new Pedido();

        // Quando executar, não lança exceção
        doNothing().when(criarPedidoUseCase).executar(pedido);

        ResponseEntity<String> response = pedidoController.criarPedido(pedido);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("Pedido criado com sucesso");
        verify(criarPedidoUseCase, times(1)).executar(pedido);
    }

    @Test
    @DisplayName("Deve retornar bad request ao lançar IllegalArgumentException")
    void deveRetornarBadRequestParaIllegalArgumentException() {
        Pedido pedido = new Pedido();
        doThrow(new IllegalArgumentException("Cliente inválido")).when(criarPedidoUseCase).executar(pedido);

        ResponseEntity<String> response = pedidoController.criarPedido(pedido);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Erro ao criar pedido");
        assertThat(response.getBody()).contains("Cliente inválido");
    }

    @Test
    @DisplayName("Deve retornar bad request ao lançar IllegalStateException")
    void deveRetornarBadRequestParaIllegalStateException() {
        Pedido pedido = new Pedido();
        doThrow(new IllegalStateException("Erro interno")).when(criarPedidoUseCase).executar(pedido);

        ResponseEntity<String> response = pedidoController.criarPedido(pedido);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("Erro ao criar pedido");
        assertThat(response.getBody()).contains("Erro interno");
    }

    @Test
    @DisplayName("Deve retornar internal server error para exceção inesperada")
    void deveRetornarInternalServerErrorParaExcecaoInesperada() {
        Pedido pedido = new Pedido();
        doThrow(new RuntimeException("Erro desconhecido")).when(criarPedidoUseCase).executar(pedido);

        ResponseEntity<String> response = pedidoController.criarPedido(pedido);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).contains("Erro inesperado");
        assertThat(response.getBody()).contains("Erro desconhecido");
    }
}

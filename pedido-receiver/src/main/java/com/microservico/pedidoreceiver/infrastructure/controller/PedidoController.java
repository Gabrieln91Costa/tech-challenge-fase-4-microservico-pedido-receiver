package com.microservico.pedidoreceiver.infrastructure.controller;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.application.usecase.CriarPedidoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase) {
        this.criarPedidoUseCase = criarPedidoUseCase;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody Pedido pedido) {
        try {
            criarPedidoUseCase.executar(pedido);
            return ResponseEntity.ok("✅ ETAPA 04 - Pedido criado com sucesso!!!!");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body("Erro ao criar pedido: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Erro inesperado: " + ex.getMessage());
        }
    }
}

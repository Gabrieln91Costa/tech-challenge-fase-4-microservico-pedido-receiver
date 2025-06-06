package com.microservico.pedidoreceiver.application.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

import com.microservico.pedidoreceiver.application.exception.ClienteNaoEncontradoException;
import com.microservico.pedidoreceiver.application.exception.ProdutoNaoEncontradoException;
import com.microservico.pedidoreceiver.domain.model.ItemPedido;
import com.microservico.pedidoreceiver.domain.model.Pedido;
import com.microservico.pedidoreceiver.domain.model.StatusPedido;
import com.microservico.pedidoreceiver.domain.repository.PedidoMongoRepository;
import com.microservico.pedidoreceiver.infrastructure.messaging.PedidoProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CriarPedidoUseCaseTest {

    private PedidoMongoRepository pedidoRepository;
    private PedidoProducer pedidoProducer;
    private RestTemplate restTemplate;

    private CriarPedidoUseCase criarPedidoUseCase;

    @BeforeEach
    public void setup() {
        pedidoRepository = mock(PedidoMongoRepository.class);
        pedidoProducer = mock(PedidoProducer.class);
        restTemplate = mock(RestTemplate.class);
        criarPedidoUseCase = new CriarPedidoUseCase(pedidoRepository, pedidoProducer, restTemplate);
    }

    @Test
    public void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Simula 404 ao consultar cliente
        doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND))
                .when(restTemplate).getForEntity(startsWith("http://cliente.service/api/clientes/"), eq(Void.class));

        Pedido pedido = new Pedido();
        pedido.setCpfCliente("12345678900");

        // Pode deixar a lista de itens vazia para esse teste
        pedido.setItens(List.of());

        // Espera exceção ClienteNaoEncontradoException
        assertThrows(ClienteNaoEncontradoException.class, () -> criarPedidoUseCase.executar(pedido));

        // Verifica que não salvou nem enviou pedido
        verifyNoInteractions(pedidoRepository);
        verifyNoInteractions(pedidoProducer);
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoExiste() {
        Pedido pedido = new Pedido();
        pedido.setCpfCliente("12345678900");

        ItemPedido item = new ItemPedido();
        item.setSku("SKU-123");
        item.setQuantidade(1);

        pedido.setItens(List.of(item));

        // configurar o mock do restTemplate para lançar NOT_FOUND ao buscar produto "SKU-123"
        // Exemplo (usando Mockito):
        when(restTemplate.getForEntity("http://produto.service/api/produtos/SKU-123", Void.class))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ProdutoNaoEncontradoException.class, () -> criarPedidoUseCase.executar(pedido));
    }

    @Test
    public void deveCriarPedidoQuandoClienteEProdutoExistem() {
        when(restTemplate.getForEntity(startsWith("http://cliente.service/api/clientes/"), eq(Void.class)))
                .thenReturn(null);

        when(restTemplate.getForEntity(startsWith("http://produto.service/api/produtos/"), eq(Void.class)))
                .thenReturn(null);

        Pedido pedido = new Pedido();
        pedido.setCpfCliente("12345678900");

        ItemPedido item = new ItemPedido();
        item.setSku("SKU-123");
        item.setQuantidade(1);

        pedido.setItens(List.of(item));

        criarPedidoUseCase.executar(pedido);

        // Verifica status e interações
        assert(pedido.getStatus() == StatusPedido.ABERTO);
        verify(pedidoRepository).save(pedido);
        verify(pedidoProducer).enviarPedido(pedido);
    }
}

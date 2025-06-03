package com.microservico.pedidoreceiver.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Pedido {

    private String id;
    private String cpfCliente;       // novo
    private String numeroCartao;     // novo
    private Double valorTotal;       // novo

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusPedido status;     // adaptar para o enum esperado pelo consumidor

    private List<ItemPedido> itens;

    public Pedido() {}

    // getters e setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }
    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }
    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}

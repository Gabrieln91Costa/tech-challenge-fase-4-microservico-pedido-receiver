package com.microservico.pedidoreceiver.domain.model;

import java.io.Serializable;

public class ItemPedido implements Serializable {

    private String sku;
    private Integer quantidade;
    private Double precoUnitario;

    // Construtor padrão para Jackson
    public ItemPedido() {}

    // Getters e setters
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}

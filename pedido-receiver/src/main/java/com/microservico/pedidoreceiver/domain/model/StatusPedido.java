package com.microservico.pedidoreceiver.domain.model;


public enum StatusPedido {
    ABERTO,
    FECHADO_COM_SUCESSO,
    FECHADO_SEM_CREDITO,
    FECHADO_SEM_ESTOQUE,
    CANCELADO
}
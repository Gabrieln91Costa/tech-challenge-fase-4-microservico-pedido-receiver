package com.microservico.pedidoreceiver.domain.repository;

import com.microservico.pedidoreceiver.domain.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoMongoRepository extends MongoRepository<Pedido, String> {}

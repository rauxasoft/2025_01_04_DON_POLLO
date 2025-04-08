package com.sinensia.donpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}

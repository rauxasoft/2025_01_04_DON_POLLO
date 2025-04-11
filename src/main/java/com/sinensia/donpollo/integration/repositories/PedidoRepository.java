package com.sinensia.donpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	List<Pedido> findByEstablecimientoId(Long idEstablecimiento);

	List<Pedido> findByFechaHoraBetweenOrderByFechaHora(Date desde, Date hasta);

}

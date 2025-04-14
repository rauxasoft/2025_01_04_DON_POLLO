package com.sinensia.donpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.donpollo.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	List<Pedido> findByEstablecimientoId(Long idEstablecimiento);

	List<Pedido> findByFechaHoraBetweenOrderByFechaHora(Date desde, Date hasta);

	@Query("""
			
			SELECT p.id, 
			       UPPER(p.establecimiento.nombre), 
			       p.fechaHora, 
			       p.estado,
			       UPPER(CONCAT(p.dependiente.apellido1, ' ',p.dependiente.apellido2, ', ', p.dependiente.nombre))
			  FROM Pedido p
			
			""")
	List<Object[]> getDTO1();
}

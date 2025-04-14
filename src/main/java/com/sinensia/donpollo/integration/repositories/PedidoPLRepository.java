package com.sinensia.donpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.donpollo.integration.model.PedidoPL;

public interface PedidoPLRepository extends JpaRepository<PedidoPL, Long>{

	List<PedidoPL> findByEstablecimientoId(Long idEstablecimiento);

	List<PedidoPL> findByFechaHoraBetweenOrderByFechaHora(Date desde, Date hasta);
	
	@Query("""
			
			SELECT p.id, 
			       UPPER(p.establecimiento.nombre), 
			       p.fechaHora, 
			       p.estado,
			       UPPER(
			       			CONCAT(
			       					p.dependiente.apellido1, 
			       					CASE
			       						WHEN p.dependiente.apellido2 IS NOT NULL THEN CONCAT (' ', p.dependiente.apellido2, ', ')
			       						ELSE ', '
			       					END,
			       					p.dependiente.nombre
			       			)
			       )
			  FROM PedidoPL p
			
			""")
	List<Object[]> getDTO1();
	
}

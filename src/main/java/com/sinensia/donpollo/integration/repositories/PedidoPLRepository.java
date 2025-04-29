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
	
	// TODO test de la query
	
	@Query("""
	            SELECT e.nombre, COUNT(p.id) 
	            FROM PedidoPL p RIGHT JOIN EstablecimientoPL e ON p.establecimiento = e
	            GROUP BY e 
	        """)
	 List<Object[]> getEstadisticaNumeroPedidosByEstablecimiento();
	 
	// TODO test de la query
	 
	 @Query(""" 
		        SELECT e.nombre, COUNT(p) 
		        FROM EstablecimientoPL e LEFT JOIN PedidoPL p ON p.establecimiento = e
		        WHERE p.fechaHora BETWEEN :desde AND :hasta
		        GROUP BY e
		    """)
	  List<Object[]> getEstadisticaNumeroPedidosByEstablecimiento(Date desde, Date hasta);
	  
	  // *************************************************

	  @Query("""
                  
                  SELECT  e.nombre, ROUND(AVG(l.precio * l.cantidad), 2)
                    FROM  EstablecimientoPL e LEFT JOIN PedidoPL p ON p.establecimiento = e JOIN p.lineas l
                   WHERE  p.fechaHora BETWEEN :desde AND :hasta
                GROUP BY  e
            """)
	  List<Object[]> getEstadisticaImporteMedioByEstablecimiento(Date desde, Date hasta);

	  @Query("""
                  SELECT  e.nombre, ROUND(AVG(l.precio * l.cantidad), 2)
                    FROM  EstablecimientoPL e LEFT JOIN PedidoPL p ON p.establecimiento = e JOIN p.lineas l
                GROUP BY  e
            """)
	  List<Object[]> getEstadisticaImporteMedioByEstablecimiento();

	  @Query("""
                  SELECT  d.nombre, COUNT(p)
                    FROM  DependientePL d LEFT JOIN PedidoPL p ON p.dependiente = d
                GROUP BY  d
            """)
	  List<Object[]> getEstadisticaNumeroPedidosByDependiente();
	  
	  @Query("""
                  SELECT  d.nombre, COUNT(p)
                    FROM  DependientePL d LEFT JOIN PedidoPL p ON p.dependiente = d
                   WHERE  p.fechaHora BETWEEN :desde AND :hasta
                GROUP BY  d
          """)
	  List<Object[]> getEstadisticaNumeroPedidosByDependiente(Date desde, Date hasta);

}

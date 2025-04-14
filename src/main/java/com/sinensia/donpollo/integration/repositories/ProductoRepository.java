package com.sinensia.donpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByFamiliaId(Long idFamilia);
	
	List<Producto> findByPrecioBetweenOrderByPrecio(double min, double max);
	
	List<Producto> findByDescatalogadoTrue();
	
	List<Producto> findByFechaAltaBetweenOrderByFechaAlta(Date desde, Date hasta);

	@Query("""
			
			SELECT UPPER(CONCAT(p.nombre, ' [',  p.familia.nombre, ']')), p.precio
				FROM Producto p
			
			""")
	List<Object[]> getDTO1();
	
	@Query("""
			
			SELECT 
			 	UPPER(p.nombre), 
			 	CASE
			 		WHEN p.descripcion IS NULL THEN ''
			 		ELSE
			 			CASE 
			     			WHEN LENGTH(p.descripcion) < 25 THEN p.descripcion 
			     			ELSE CONCAT(SUBSTRING(p.descripcion, 1, 22), '...') 
			 			END
			 	END		
				FROM Producto p
			
			""")
	List<Object[]> getDTO2();

	@Modifying
	@Query("""
			UPDATE Producto p
			   SET p.precio = p.precio + (p.precio * :incremental) / 100 
			WHERE p.familia = :familia
			""")
	void updatePrecios(Familia familia, double incremental);
	
	@Modifying
	@Query("""
			UPDATE Producto p
			   SET p.precio = p.precio + (p.precio * :incremental) / 100 
			 WHERE p.id IN :productos
			""")
	void updatePrecios(Long[] productos, double incremental);
	
	@Modifying
	@Query("""
			UPDATE Producto p
			   SET p.precio = p.precio + (p.precio * :incremental) / 100 
			 WHERE p IN :productos
			""")
	void updatePrecios(List<Producto> productos, double incremental);
	
}

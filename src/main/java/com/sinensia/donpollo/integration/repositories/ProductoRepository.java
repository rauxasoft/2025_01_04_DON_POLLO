package com.sinensia.donpollo.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByFamiliaId(Long idFamilia);
	
	List<Producto> findByPrecioBetweenOrderByPrecio(double min, double max);
	
	List<Producto> findByDescatalogadoTrue();
	
	List<Producto> findByFechaAltaBetweenOrderByFechaAlta(Date desde, Date hasta);

}

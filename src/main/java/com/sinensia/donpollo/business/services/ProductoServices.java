package com.sinensia.donpollo.business.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.model.dtos.Oferta;
import com.sinensia.donpollo.business.model.dtos.ProductoDTO1;
import com.sinensia.donpollo.business.model.dtos.ProductoDTO2;

public interface ProductoServices {

	/**
	 * Si la id no es null lanza BusinessException
	 * 
	 */
	Long create(Producto producto);										
	
	Optional<Producto> read(Long idProducto);							
	
	/**
	 * Si la id es null o no existe lanza BusinessException
	 * 
	 */
	void update(Producto producto);										
	
	/**
	 * Si la id es null o no existe lanza BusinessException
	 * 
	 */
	void delete(Long idProducto);										
	
	List<Producto> getAll();
	
	List<Producto> getByFamilia(Familia familia);
	List<Producto> getByPrecioBetween(double min, double max);
	List<Producto> getDescatalogados();
	List<Producto> getByFechaAltaBetween(Date desde, Date hasta);
	
	int getNumeroTotalProductos();
	
	int getNumeroTotalProductosByFamilia(Familia familia);
	
	/**
	 * Incrementa el precio de los productos por Familia
	 *  
	 * Ejemplo:
	 * 
	 * Si porcentaje = 2.0 => el producto se incrementa un 2%
	 * 
	 * Si el porcentaje es <= 0 se lanza BusinessException
	 * 
	 */
	void incrementarPrecios(Familia familia, double porcentaje);
	
	/**
	 * Incrementa el precio de la lista de productos que se aporta
	 *  
	 * Ejemplo:
	 * 
	 * Si porcentaje = 2.0 => el producto se incrementa un 2%
	 * 
	 * Si el porcentaje es <= 0 se lanza BusinessException
	 * 
	 */
	void incrementarPrecios(List<Producto> productos, double porcentaje);
	
	/**
	 * Incrementa el precio de los prouctos referenciados por id
	 *  
	 * Ejemplo:
	 * 
	 * Si porcentaje = 2.0 => el producto se incrementa un 2%
	 * 
	 * Si el porcentaje es <= 0 se lanza BusinessException
	 * 
	 */
	void incrementarPrecios(Long[] idsProducto, double porcentaje);

	/**
	 * Devuelve a través de un mapa la cantidad de productos por Familia
	 * 
	 */
	Map<Familia, Integer> getEstadisticaNumeroProductosByFamilia();
	
	/**
	 * Devuelve a través de un mapa el precio medio de los productos por Familia
	 * 
	 */
	Map<Familia, Double> getEstadisticaPrecioMedioProductosByFamilia();
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************
	
	List<ProductoDTO1> getDTO1();
	
	List<ProductoDTO2> getDTO2();
	
	List<Oferta> getOfertas();
}

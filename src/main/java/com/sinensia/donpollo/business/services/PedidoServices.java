package com.sinensia.donpollo.business.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.model.dtos.PedidoDTO1;

public interface PedidoServices {

	/**
	 * Si la id no es null lanza BusinessException
	 * 
	 */
	Long create(Pedido pedido);										
	
	Optional<Pedido> read(Long idPedido);							
	
	/**
	 * Si la id es null o no existe lanza BusinessException
	 * 
	 * Si se intenta transicionar entre estados no validos se lanza IllegalStateException
	 * 
	 */
	void update(Pedido pedido);										
	
	List<Pedido> getAll();
	
	List<Pedido> getByIdEstablecimiento(Long idEstablecimiento);
	List<Pedido> getBetweenFechas(Date desde, Date hasta);
	
	int getNumeroTotalPedidos();
	
	/**
	 * Devuelve estadística del número de pedidos por establecimiento
	 * 
	 * La estadística devuelve el nombre (String) del establecimiento
	 *
	 */
	Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento();
	
	/**
	 * Devuelve estadística del número de pedidos por establecimiento en un periodo
	 * 
	 * La estadística devuelve el nombre (String) del establecimiento
	 *
	 */
	Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento(Date desde, Date hasta);
	
	/**
	 * Devuelve estadística del importe medio de los pedidos en un periodo
	 * 
	 * La estadística devuelve el nombre (String) del establecimiento
	 *
	 */
	Map<String, Double> getEstadisticaImporteMedioByEstablecimiento(Date desde, Date hasta);
	
	/**
	 * Devuelve estadística del importe medio de los pedidos
	 * 
	 * La estadística devuelve el nombre (String) del establecimiento
	 *
	 */
	Map<String, Double> getEstadisticaImporteMedioByEstablecimiento();
	
	/**
	 * Devuelve estadística del número de pedidos realizados por cada dependiente
	 * 
	 * La estadística devuelve el nombre completo (String) del dependiente
	 *
	 */
	Map<String, Integer> getEstadisticaNumeroPedidosByDependiente();
	
	/**
	 * Devuelve estadística del número de pedidos realizados por cada dependiente en un periodo
	 * 
	 * La estadística devuelve el nombre completo (String) del dependiente
	 *
	 */
	Map<String, Integer> getEstadisticaNumeroPedidosByDependiente(Date desde, Date hasta);
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************
	
	List<PedidoDTO1> getDTO1();
	
}

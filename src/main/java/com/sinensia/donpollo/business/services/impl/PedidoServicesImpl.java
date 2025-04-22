package com.sinensia.donpollo.business.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.model.dtos.PedidoDTO1;
import com.sinensia.donpollo.business.services.PedidoServices;
import com.sinensia.donpollo.integration.model.PedidoPL;
import com.sinensia.donpollo.integration.model.EstadoPedidoPL;
import com.sinensia.donpollo.integration.repositories.PedidoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{

	private SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");
	
	private final PedidoPLRepository pedidoPLRepository;
	private final DozerBeanMapper mapper;
	
	public PedidoServicesImpl(PedidoPLRepository pedidoRepository, DozerBeanMapper mapper) {
		this.pedidoPLRepository = pedidoRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Pedido pedido) {
		
		if(pedido.getId() != null) {
			throw new BusinessException("Para crear un pedido la id ha de ser null");
		}
		
		PedidoPL pedidoPL = mapper.map(pedido, PedidoPL.class);

		return pedidoPLRepository.save(pedidoPL).getId();
	}

	@Override
	public Optional<Pedido> read(Long idPedido) {
		
		return pedidoPLRepository.findById(idPedido).stream()
				.map(x -> mapper.map(x, Pedido.class))
				.findAny();
	}

	@Override
	@Transactional
	public void update(Pedido pedido) {
		
		Long id = pedido.getId();
		
		if(id == null) {
			throw new BusinessException("La id del pedido no puede ser null");
		}
		
		boolean existe = pedidoPLRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el pedido con id [" + id + "]");
		}
		
		/*
		EstadoPedido estadoNuevo = pedido.getEstado();
		EstadoPedido estadoAnterior =pedidoPLRepository.findById(id).get().getEstado();
		
		if(estadoAnterior.equals(EstadoPedido.CANCELADO) && !estadoNuevo.equals(EstadoPedido.CANCELADO)) {
			throw new IllegalStateException("CANCELADO es un estado final.");
		}
		
		*/
		
		// TODO Comprobar validez de estados!
		
		pedidoPLRepository.save(mapper.map(pedido, PedidoPL.class));
	}

	@Override
	public List<Pedido> getAll() {
		return convertListFromIntegrationToBusiness(pedidoPLRepository.findAll());
	}

	@Override
	public List<Pedido> getByIdEstablecimiento(Long idEstablecimiento) {	
		return convertListFromIntegrationToBusiness(pedidoPLRepository.findByEstablecimientoId(idEstablecimiento));
	}

	@Override
	public List<Pedido> getBetweenFechas(Date desde, Date hasta) {
		return convertListFromIntegrationToBusiness(pedidoPLRepository.findByFechaHoraBetweenOrderByFechaHora(desde, hasta));
	}

	@Override
	public int getNumeroTotalPedidos() {
		return (int) pedidoPLRepository.count();
	}

	@Override
	public Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> getEstadisticaImporteMedioByEstablecimiento(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> getEstadisticaImporteMedioByEstablecimiento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getEstadisticaNumeroPedidosByDependiente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getEstadisticaNumeroPedidosByDependiente(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************
	
	@Override
	public List<PedidoDTO1> getDTO1() {
		
		return pedidoPLRepository.getDTO1().stream()
				.map(fila -> {
					
					int id = ((Long) fila[0]).intValue();
					String nomEstablecimiento = (String) fila[1];
					Date fecha = (Date) fila[2];
					EstadoPedidoPL estadoPedidoPL = (EstadoPedidoPL) fila[3];
					String dependiente = (String) fila[4];
			        String strFecha = formateadorFecha.format(fecha);
			        String strHora = formateadorHora.format(fecha);

					return new PedidoDTO1(id, nomEstablecimiento, strFecha, strHora, estadoPedidoPL.toString(), dependiente);
				}).toList();
	}
	
	// *******************************************************
	//
	// Private Methods
	//
	// *******************************************************	
	
	private List<Pedido> convertListFromIntegrationToBusiness(List<PedidoPL> pedidosPL){
		
		return pedidosPL.stream()
				.map(x -> mapper.map(x, Pedido.class))
				.toList();
	}
	
}

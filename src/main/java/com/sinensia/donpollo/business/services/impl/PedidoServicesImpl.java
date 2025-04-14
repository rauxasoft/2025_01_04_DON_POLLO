package com.sinensia.donpollo.business.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.EstadoPedido;
import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.model.dtos.PedidoDTO1;
import com.sinensia.donpollo.business.services.PedidoServices;
import com.sinensia.donpollo.integration.repositories.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{

	private SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");
	
	private final PedidoRepository pedidoRepository;
	
	public PedidoServicesImpl(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	@Override
	@Transactional
	public Long create(Pedido pedido) {
		
		if(pedido.getId() != null) {
			throw new BusinessException("Para crear un pedido la id ha de ser null");
		}
		
		return pedidoRepository.save(pedido).getId();
	}

	@Override
	public Optional<Pedido> read(Long idPedido) {
		return pedidoRepository.findById(idPedido);
	}

	@Override
	@Transactional
	public void update(Pedido pedido) {
		
		Long id = pedido.getId();
		
		if(id == null) {
			throw new BusinessException("La id del pedido no puede ser null");
		}
		
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el pedido con id [\" + id + \"]");
		}
		
		EstadoPedido estadoNuevo = pedido.getEstado();
		EstadoPedido estadoAnterior =pedidoRepository.findById(id).get().getEstado();
		
		if(estadoAnterior.equals(EstadoPedido.CANCELADO) && !estadoNuevo.equals(EstadoPedido.CANCELADO)) {
			throw new IllegalStateException("CANCELADO es un estado final.");
		}
		
		// TODO
		
		
		pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> getAll() {
		return pedidoRepository.findAll();
	}

	@Override
	public List<Pedido> getByIdEstablecimiento(Long idEstablecimiento) {	
		return pedidoRepository.findByEstablecimientoId(idEstablecimiento);
	}

	@Override
	public List<Pedido> getBetweenFechas(Date desde, Date hasta) {
		return pedidoRepository.findByFechaHoraBetweenOrderByFechaHora(desde, hasta);
	}

	@Override
	public int getNumeroTotalPedidos() {
		return (int) pedidoRepository.count();
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
		
		return pedidoRepository.getDTO1().stream()
				.map(fila -> {
					
					int id = ((Long) fila[0]).intValue();
					String nomEstablecimiento = (String) fila[1];
					Date fecha = (Date) fila[2];
					EstadoPedido estado = (EstadoPedido) fila[3];
					String dependiente = (String) fila[4];
			        String strFecha = formateadorFecha.format(fecha);
			        String strHora = formateadorHora.format(fecha);

					return new PedidoDTO1(id, nomEstablecimiento, strFecha, strHora, estado.toString(), dependiente);
				}).toList();
	}
	
}

package com.sinensia.donpollo.business.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

	// TODO Test unitario de todos los métodos de este servicio
	
	@Override
    public Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento() {
        
        List<Object[]> resultados = pedidoPLRepository.getEstadisticaNumeroPedidosByEstablecimiento();
       
        Map<String, Integer> estadistica = new HashMap<>();

        resultados.forEach(resultado -> {
            estadistica.put((String)resultado[0], ((Long) resultado[1]).intValue());
        });

        return estadistica;
    }

	@Override
	public Map<String, Integer> getEstadisticaNumeroPedidosByEstablecimiento(Date desde, Date hasta) {
		 
		List<Object[]> resultados =  pedidoPLRepository.getEstadisticaNumeroPedidosByEstablecimiento();

	    Map<String, Integer> estadistica = new HashMap<>();
	        
	    resultados.forEach(resultado -> {
	    	estadistica.put((String)resultado[0], ((Long) resultado[1]).intValue());     
	    });
	     
	    return estadistica;
	}

	@Override
	public Map<String, Double> getEstadisticaImporteMedioByEstablecimiento(Date desde, Date hasta) {
	        
	    List<Object[]> lista = pedidoPLRepository.getEstadisticaImporteMedioByEstablecimiento(desde, hasta);

	     Map<String, Double> resultado = new HashMap<>();

	      lista.stream().forEach(fila -> {
	            resultado.put((String) fila[0], (Double) fila[1]);
	     });

	     return resultado;
	 }

	 @Override
	 public Map<String, Double> getEstadisticaImporteMedioByEstablecimiento() {

	     List<Object[]> lista = pedidoPLRepository.getEstadisticaImporteMedioByEstablecimiento();

	        Map<String, Double> resultado = new HashMap<>();

	        lista.stream().forEach(fila -> {
	            resultado.put((String) fila[0], (Double) fila[1]);
	        });

	        return resultado;
	  }

	  // TODO Los dependientes vienen con el nombre completo y su ID en formato "[2334] López Ridruejo, Fermín"
	   
	  @Override
	 public Map<String, Integer> getEstadisticaNumeroPedidosByDependiente() {

	        List<Object[]> resultados = pedidoPLRepository.getEstadisticaNumeroPedidosByDependiente();

	        Map<String, Integer> estadistica = new HashMap<>();

	        resultados.stream().forEach(fila -> {
	        	estadistica.put((String) fila[0], ((Long) fila[1]).intValue());
	        });
	        
	        return estadistica;
	 }

	 @Override
	 public Map<String, Integer> getEstadisticaNumeroPedidosByDependiente(Date desde, Date hasta) {
		 
		 List<Object[]> resultados = pedidoPLRepository.getEstadisticaNumeroPedidosByDependiente(desde, hasta);
		 
		 Map<String, Integer> estadistica = new HashMap<>();
		 
		 resultados.stream().forEach(fila -> {
	        estadistica.put((String) fila[0], ((Long) fila[1]).intValue());
	     });
		 
		 return estadistica;
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

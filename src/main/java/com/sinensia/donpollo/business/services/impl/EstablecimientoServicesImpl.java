package com.sinensia.donpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO1;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO2;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO3;
import com.sinensia.donpollo.business.services.EstablecimientoServices;
import com.sinensia.donpollo.integration.model.EstablecimientoPL;
import com.sinensia.donpollo.integration.repositories.EstablecimientoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices {

	private final EstablecimientoPLRepository establecimientoPLRepository;
	private final DozerBeanMapper mapper;
	
	public EstablecimientoServicesImpl(EstablecimientoPLRepository establecimientoPLRepository, DozerBeanMapper mapper) {
		this.establecimientoPLRepository = establecimientoPLRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Establecimiento establecimiento) {
		
		if(establecimiento.getId() != null) {
			throw new BusinessException("Para crear un establecimiento la id ha de ser null");
		}
		
		EstablecimientoPL establecimientoPL = mapper.map(establecimiento, EstablecimientoPL.class);
		
		return establecimientoPLRepository.save(establecimientoPL).getId();
	}

	@Override
	public Optional<Establecimiento> read(Long idEstablecimiento) {
		
		return establecimientoPLRepository.findById(idEstablecimiento).stream()
				.map(x -> mapper.map(x, Establecimiento.class))
				.findAny();
	}

	@Override
	@Transactional
	public void update(Establecimiento establecimiento) {
		
		Long id = establecimiento.getId();
		
		if(id == null) {
			throw new BusinessException("La id de establecimiento no puede ser null");
		}
		
		boolean existe = establecimientoPLRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el establecimiento con id [" + id + "]");
		}
		
		EstablecimientoPL establecimientoPL = mapper.map(establecimiento, EstablecimientoPL.class);
		
		establecimientoPLRepository.save(establecimientoPL);
		
	}

	@Override
	public List<Establecimiento> getAll() {
		
		return establecimientoPLRepository.findAll().stream()
				.map(x -> mapper.map(x, Establecimiento.class))
				.toList();
	}
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************

	@Override
	public List<EstablecimientoDTO1> getDTO1() {
		
		return establecimientoPLRepository.getDTO1().stream()
				.map(fila -> {
					EstablecimientoDTO1 establecimientoDTO1 = new EstablecimientoDTO1();
					establecimientoDTO1.setNombre((String) fila[0]);
					establecimientoDTO1.setTele1((String) fila[1]);
					establecimientoDTO1.setCodigoPostal((String) fila[2]);
					return establecimientoDTO1;
				})
				.toList();
	}

	@Override
	public List<EstablecimientoDTO2> getDTO2() {
		
		return establecimientoPLRepository.getDTO2().stream()
				.map(fila -> {
					String nombre = (String) fila[0];
					String provincia = (String) fila[1];
					String telefono = (String) fila[2];
					String email = (String) fila[3];
					return new EstablecimientoDTO2(nombre, provincia, telefono, email);
				})
				.toList();	
	}

	@Override
	public List<EstablecimientoDTO3> getDTO3() {
		
		return establecimientoPLRepository.getDTO3().stream()
				.map(fila -> {
					String nombre = (String) fila[0];
					String datos = (String) fila[1];
			
					return new EstablecimientoDTO3(nombre, datos);
				})
				.toList();	
	}

}

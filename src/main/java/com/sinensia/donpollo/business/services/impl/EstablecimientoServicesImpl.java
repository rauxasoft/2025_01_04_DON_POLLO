package com.sinensia.donpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.services.EstablecimientoServices;
import com.sinensia.donpollo.integration.repositories.EstablecimientoRepository;

import jakarta.transaction.Transactional;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices {

	private final EstablecimientoRepository establecimientoRepository;
	
	public EstablecimientoServicesImpl(EstablecimientoRepository establecimientoRepositor) {
		this.establecimientoRepository = establecimientoRepositor;
	}
	
	@Override
	@Transactional
	public Long create(Establecimiento establecimiento) {
		
		if(establecimiento.getId() != null) {
			throw new BusinessException("Para crear un establecimiento la id ha de ser null");
		}
		
		Establecimiento createdEstablecimiento = establecimientoRepository.save(establecimiento);
		
		return createdEstablecimiento.getId();
	}

	@Override
	public Optional<Establecimiento> read(Long idEstablecimiento) {
		return establecimientoRepository.findById(idEstablecimiento);
	}

	@Override
	@Transactional
	public void update(Establecimiento establecimiento) {
		
		Long id = establecimiento.getId();
		
		if(id == null) {
			throw new BusinessException("La id de establecimiento no puede ser null");
		}
		
		boolean existe = establecimientoRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el establecimiento con id [" + id + "]");
		}
		
		establecimientoRepository.save(establecimiento);
		
	}

	@Override
	public List<Establecimiento> getAll() {
		return establecimientoRepository.findAll();
	}

}

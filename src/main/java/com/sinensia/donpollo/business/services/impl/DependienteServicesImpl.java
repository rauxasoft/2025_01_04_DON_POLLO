package com.sinensia.donpollo.business.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.services.DependienteServices;
import com.sinensia.donpollo.integration.repositories.DependienteRepository;

@Service
public class DependienteServicesImpl implements DependienteServices {

	private final DependienteRepository dependienteRepository;
	
	public DependienteServicesImpl( DependienteRepository dependienteRepository) {
		this.dependienteRepository = dependienteRepository;
	}
	
	@Override
	public List<Dependiente> getAll() {
		return dependienteRepository.findAll();
	}

}

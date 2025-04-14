package com.sinensia.donpollo.business.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.services.DependienteServices;
import com.sinensia.donpollo.integration.repositories.DependientePLRepository;

@Service
public class DependienteServicesImpl implements DependienteServices {

	private final DependientePLRepository dependientePLRepository;
	
	public DependienteServicesImpl( DependientePLRepository dependienteRepository) {
		this.dependientePLRepository = dependienteRepository;
	}
	
	@Override
	public List<Dependiente> getAll() {
		return dependientePLRepository.findAll();
	}

}

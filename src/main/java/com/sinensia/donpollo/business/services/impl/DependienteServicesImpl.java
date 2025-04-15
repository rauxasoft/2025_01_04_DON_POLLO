package com.sinensia.donpollo.business.services.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.services.DependienteServices;
import com.sinensia.donpollo.integration.repositories.DependientePLRepository;

@Service
public class DependienteServicesImpl implements DependienteServices {

	private final DependientePLRepository dependientePLRepository;
	private final DozerBeanMapper mapper;
	
	public DependienteServicesImpl( DependientePLRepository dependienteRepository, DozerBeanMapper mapper) {
		this.dependientePLRepository = dependienteRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<Dependiente> getAll() {
		
		return dependientePLRepository.findAll().stream()
				.map(x -> mapper.map(x, Dependiente.class))
				.toList();
				
	}

}

package com.sinensia.donpollo.business.services.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.services.FamiliaServices;
import com.sinensia.donpollo.integration.model.FamiliaPL;
import com.sinensia.donpollo.integration.repositories.FamiliaPLRepository;

import jakarta.transaction.Transactional;

@Service
public class FamiliaServicesImpl implements FamiliaServices {

	private final FamiliaPLRepository familiaPLRepository;
	private final DozerBeanMapper mapper;
	
	public FamiliaServicesImpl(FamiliaPLRepository familiaRepository, DozerBeanMapper mapper) {
		this.familiaPLRepository = familiaRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Familia familia) {
		
		if(familia.getId() != null) {
			throw new IllegalStateException("Para crear una familia la id ha de ser null");
		}
		
		FamiliaPL familiaPL = mapper.map(familia, FamiliaPL.class);
		FamiliaPL createdFamiliaPL = familiaPLRepository.save(familiaPL);
		
		return createdFamiliaPL.getId();
	}

	@Override
	public List<Familia> getAll() {
		
		return familiaPLRepository.findAll().stream()
				.map(x -> mapper.map(x, Familia.class))
				.toList();
	}	
		
}

package com.sinensia.donpollo.business.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.services.FamiliaServices;
import com.sinensia.donpollo.integration.repositories.FamiliaRepository;

import jakarta.transaction.Transactional;

@Service
public class FamiliaServicesImpl implements FamiliaServices {

	private final FamiliaRepository familiaRepository;
	
	public FamiliaServicesImpl(FamiliaRepository familiaRepository) {
		this.familiaRepository = familiaRepository;
	}
	
	@Override
	@Transactional
	public Long create(Familia familia) {
		
		if(familia.getId() != null) {
			throw new IllegalStateException("Para crear una familia la id ha de ser null");
		}
		
		Familia createdFamilia = familiaRepository.save(familia);
		
		return createdFamilia.getId();
	}

	@Override
	public List<Familia> getAll() {
		return familiaRepository.findAll();
	}	
		
}

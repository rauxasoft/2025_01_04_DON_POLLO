package com.sinensia.donpollo.business.services.dummy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.services.FamiliaServices;

@Service
@Primary
public class FamiliaServicesDummyImpl implements FamiliaServices {

private final Map<Long, Familia> FAMILIA_DB = new HashMap<>();
	
	public FamiliaServicesDummyImpl() {
		initData();
	}
	
	@Override
	public Long create(Familia familia) {

		if(familia.getId() != null) {
			throw new IllegalStateException("Para crear una familia la id ha de ser null");
		}
		
		Long id = System.currentTimeMillis();
		
		familia.setId(id);
		
		FAMILIA_DB.put(id, familia);
	
		return id;
	}

	@Override
	public List<Familia> getAll() {
		return new ArrayList<>(FAMILIA_DB.values());
	}

	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private void initData() {
		
		Familia familia1 = new Familia();
		Familia familia2 = new Familia();
		Familia familia3 = new Familia();
		
		familia1.setId(100L);
		familia2.setId(101L);
		familia3.setId(102L);
		
		familia1.setNombre("TAPAS");
		familia2.setNombre("REFRESCOS");
		familia3.setNombre("LICORES");
		
		FAMILIA_DB.put(familia1.getId(), familia1);
		FAMILIA_DB.put(familia2.getId(), familia2);
		FAMILIA_DB.put(familia3.getId(), familia3);
	}
			
}

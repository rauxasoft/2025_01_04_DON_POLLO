package com.sinensia.donpollo.business.services;

import java.util.List;

import com.sinensia.donpollo.business.model.Familia;

public interface FamiliaServices {

	/**
	 * Si la id no es null lanza IllegalStateException
	 * 
	 */
	Long create(Familia familia);
	
	List<Familia> getAll();
	
}

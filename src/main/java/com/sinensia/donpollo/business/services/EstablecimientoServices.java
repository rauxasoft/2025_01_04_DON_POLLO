package com.sinensia.donpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.donpollo.business.model.Establecimiento;

public interface EstablecimientoServices {

	/**
	 * Si la id no es null lanza BusinessException
	 * 
	 */
	Long create(Establecimiento establecimiento);
	
	Optional<Establecimiento> read(Long idEstablecimiento);
	
	/**
	 * Si la id es null o no existe lanza BusinessException
	 * 
	 */
	void update(Establecimiento establecimiento);
	
	List<Establecimiento> getAll();
	
}

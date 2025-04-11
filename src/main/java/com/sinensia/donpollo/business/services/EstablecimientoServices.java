package com.sinensia.donpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO1;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO2;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO3;

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
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************
	
	List<EstablecimientoDTO1> getDTO1();
	
	List<EstablecimientoDTO2> getDTO2();
	
	List<EstablecimientoDTO3> getDTO3();
		
}

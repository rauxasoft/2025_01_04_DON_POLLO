package com.sinensia.donpollo.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO3;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {

	@Query("""
			
			SELECT UPPER(e.nombre),           
		           e.datosContacto.telefono1, 
		           e.direccion.codigoPostal   
		      FROM Establecimiento e          
			
	""")
	List<Object[]> getDTO1();

	@Query("""
			
			SELECT e.nombre,                  
		           e.direccion.provincia,     
		           e.datosContacto.telefono1, 
		           e.datosContacto.email      
		      FROM Establecimiento e
		              
	""")
	List<Object[]> getDTO2();
	
	@Query("""
			
			SELECT new com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO3(
				          UPPER(e.nombre), 
						  UPPER(CONCAT(e.direccion.poblacion, ' [', e.direccion.provincia,'] - ', e.datosContacto.telefono1))) 
			  FROM Establecimiento e
		 
	""")
	List<EstablecimientoDTO3> getDTO3();
}

package com.sinensia.donpollo.presentation.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.services.EstablecimientoServices;
import com.sinensia.donpollo.business.services.impl.EstablecimientoServicesImpl;

@RestController
public class EstablecimientoBorrameController {

	private EstablecimientoServices establecimientoServices = new EstablecimientoServicesImpl();
	
	// GET localhost:8080/prueba
	
	@GetMapping("/establecimiento/prueba6")
	public String trigger6() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/establecimiento/prueba5")
	public String trigger5() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/establecimiento/prueba4")
	public String trigger4() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/establecimiento/prueba3")
	public String trigger3() {
	
		// TODO
		
		return "ok"; 
	}
	@GetMapping("/establecimiento/prueba2")
	public String trigger2() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/establecimiento/prueba1")
	public String trigger1() {
		
		Optional<Establecimiento> optional = establecimientoServices.read(101L);
		
		if(optional.isPresent()) {
			System.out.println(optional.get());
		} else {
			System.out.println("No existe el producto");
		}
		
		return "ok"; 
	}
	
	
	
}

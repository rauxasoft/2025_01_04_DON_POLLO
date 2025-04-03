package com.sinensia.donpollo.presentation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.services.EstablecimientoServices;

@RestController
public class EstablecimientoController {
	
	private final EstablecimientoServices establecimientoServices;
	
	public EstablecimientoController(EstablecimientoServices establecimientoServices) {
		this.establecimientoServices = establecimientoServices;
	}

	@GetMapping("/establecimientos")
	public List<Establecimiento> getAll(){
		return establecimientoServices.getAll();
	}

}

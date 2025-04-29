package com.sinensia.donpollo.presentation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.services.DependienteServices;

@RestController
@RequestMapping("/dependientes")
@CrossOrigin
public class DependienteController {
	
	private final DependienteServices dependienteServices;
	
	public DependienteController(DependienteServices dependienteServices) {
		this.dependienteServices = dependienteServices;
	}
	
	@GetMapping
	public List<Dependiente> getAll(){
		return dependienteServices.getAll();
	}

}

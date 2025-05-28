package com.sinensia.donpollo.presentation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.services.FamiliaServices;

@RestController
@RequestMapping("/familias")
@CrossOrigin
public class FamiliaController {
	
	private final FamiliaServices familiaServices;
	
	public FamiliaController(FamiliaServices familiaServices) {
		this.familiaServices = familiaServices;
	}
	
	@GetMapping
	public List<Familia> getAll() throws Exception {
		
		Thread.sleep(1200);
		
		return familiaServices.getAll();
	}

}

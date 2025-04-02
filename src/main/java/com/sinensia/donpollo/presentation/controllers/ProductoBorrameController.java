package com.sinensia.donpollo.presentation.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.services.ProductoServices;
import com.sinensia.donpollo.business.services.impl.ProductoServicesImpl;

@RestController
public class ProductoBorrameController {
	
	private ProductoServices productoServices = new ProductoServicesImpl();

	// GET localhost:8080/establecimiento/prueba1
	
	@GetMapping("/producto/prueba6")
	public String trigger6() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/producto/prueba5")
	public String trigger5() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/producto/prueba4")
	public String trigger4() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/producto/prueba3")
	public String trigger3() {
	
		// TODO
		
		return "ok"; 
	}
	@GetMapping("/producto/prueba2")
	public String trigger2() {
	
		// TODO
		
		return "ok"; 
	}
	
	@GetMapping("/producto/prueba1")
	public String trigger1() {
		
		// TODO
		
		return "ok"; 
	}
	
}

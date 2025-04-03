package com.sinensia.donpollo.presentation.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.services.ProductoServices;

@RestController
public class ProductoController {

	private ProductoServices productoServices;
	
	public ProductoController(ProductoServices productoServices) {
		this.productoServices = productoServices;
	}
	
	@GetMapping("/productos/estadistica1")
	public Map<Familia, Integer> getEstadistica1(){
		return productoServices.getEstadisticaNumeroProductosByFamilia();
	}
	
	@GetMapping("/productos/estadistica2")
	public Map<Familia, Double> getEstadistica2(){
		return productoServices.getEstadisticaPrecioMedioProductosByFamilia();
	}
}

package com.sinensia.donpollo.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.services.ProductoServices;

@RestController
public class ProductoController {

	private ProductoServices productoServices;
	
	public ProductoController(ProductoServices productoServices) {
		this.productoServices = productoServices;
	}

}

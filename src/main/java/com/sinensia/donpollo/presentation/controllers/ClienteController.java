package com.sinensia.donpollo.presentation.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.services.ClienteServices;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteServices clienteServices;
	
	public ClienteController(ClienteServices clienteServices) {
		this.clienteServices = clienteServices;
	}
	
	// GET  /clientes
	// GET  /clientes/{id}
	// POST /clientes
	
}

package com.sinensia.donpollo.presentation.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.services.ClienteServices;
import com.sinensia.donpollo.common.presentation.ErrorResponse;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
public class ClienteController {

	private ClienteServices clienteServices;
	
	public ClienteController(ClienteServices clienteServices) {
		this.clienteServices = clienteServices;
	}
	
	@GetMapping
	public List<Cliente> getClientes() {
		return clienteServices.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		
		Optional<Cliente> optional = clienteServices.read(id);
		
		if (optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorResponse("No existe el cliente " + id), HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());

	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Cliente cliente, UriComponentsBuilder ucb ) {

		Long id = clienteServices.create(cliente);
		URI uri = ucb.path("/clientes/{id}").build(id);
		
		return ResponseEntity.created(uri).build();

	}

}

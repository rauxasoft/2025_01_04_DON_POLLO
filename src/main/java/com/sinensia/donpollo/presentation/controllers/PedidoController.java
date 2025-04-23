package com.sinensia.donpollo.presentation.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.services.PedidoServices;
import com.sinensia.donpollo.presentation.config.ErrorResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	private PedidoServices pedidoServices;
	
	public PedidoController(PedidoServices pedidoServices) {
		this.pedidoServices = pedidoServices;
	}
	
	@GetMapping
	public ResponseEntity<?> getPedidos(@RequestParam(required = false, defaultValue = "ALL") String view){
		
		System.out.println("ESTO NO!");
		
		Object respuesta = null;
		
		view = view.toUpperCase();
		
		switch(view) {
			case "DTO1": respuesta = pedidoServices.getDTO1(); break;
			case "ALL":  respuesta = pedidoServices.getAll(); break;
			default:     respuesta = pedidoServices.getAll(); break;
		}
		
		return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPedidoById(@PathVariable Long id){
		
		Optional<Pedido> optional = pedidoServices.read(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorResponse("No existe el pedido " + id), HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pedido pedido, UriComponentsBuilder ucb){
		
		Long id = pedidoServices.create(pedido);
		URI uri = ucb.path("/pedidos/{id}").build(id);
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @RequestBody Pedido pedido) {
		
		pedido.setId(id);
		
		pedidoServices.update(pedido);
		
	}

}

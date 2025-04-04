package com.sinensia.donpollo.presentation.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sinensia.donpollo.presentation.config.ErrorResponse;
import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.services.EstablecimientoServices;

@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {
	
	private final EstablecimientoServices establecimientoServices;
	
	public EstablecimientoController(EstablecimientoServices establecimientoServices) {
		this.establecimientoServices = establecimientoServices;
	}

	@GetMapping
	public List<Establecimiento> getAll(){
		return establecimientoServices.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		
		Optional<Establecimiento> optional = establecimientoServices.read(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorResponse("No existe el establecimiento " + id), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(optional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Establecimiento establecimiento, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = establecimientoServices.create(establecimiento);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		URI uri = ucb.path("/establecimientos/{id}").build(id);
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Establecimiento establecimiento, @PathVariable Long id){
		
		establecimiento.setId(id);
		
		try {
			establecimientoServices.update(establecimiento);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> prueba(@PathVariable Long id, @RequestBody Map<String, Object> values) {
		
		Optional<Establecimiento> optional = establecimientoServices.read(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorResponse("No existe el establecimiento " + id), HttpStatus.NOT_FOUND);
		}
		
		Establecimiento establecimiento = optional.get();
		
		values.forEach((k, v) -> {
			
			if("nombre".equals(k)) {
				establecimiento.setNombre((String) v);
			}
			
			if("telefono1".equals(k)) {
				establecimiento.getDatosContacto().setTelefono1((String) v);
			}
			
			if("telefono2".equals(k)) {
				establecimiento.getDatosContacto().setTelefono2((String) v);
			}
		});
		
		establecimientoServices.update(establecimiento);
		
		return ResponseEntity.noContent().build(); 
	}

}

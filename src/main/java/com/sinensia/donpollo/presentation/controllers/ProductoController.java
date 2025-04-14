package com.sinensia.donpollo.presentation.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.services.ProductoServices;
import com.sinensia.donpollo.presentation.config.ErrorResponse;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	private ProductoServices productoServices;
	
	public ProductoController(ProductoServices productoServices) {
		this.productoServices = productoServices;
	}
	
	@GetMapping
	public ResponseEntity<?> getProductos(@RequestParam(required = false, defaultValue = "ALL") String view,
										  @RequestParam(required = false) Double min, 
										  @RequestParam(required = false) Double max) {
		
		Object respuesta = null;
		
		view = view.toUpperCase();
		
		switch(view) {
			
			case "DTO1": respuesta = productoServices.getDTO1(); break;
			case "DTO2": respuesta = productoServices.getDTO2(); break;
			case "ALL":  {
				
				if(min == null || max == null) {
					respuesta = productoServices.getAll();
				} else {
					respuesta = productoServices.getByPrecioBetween(min, max);
				}
				
				break;
			}
				
			default:     respuesta = productoServices.getAll(); break;
		}
		
		return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		
		Optional<Producto> optional = productoServices.read(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>(new ErrorResponse("No existe el producto " + id), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(optional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Producto producto, UriComponentsBuilder ucb){
		
		Long id = productoServices.create(producto);
		URI uri = ucb.path("/productos/{id}").build(id);
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Producto producto, @PathVariable Long id){
		
		producto.setId(id);
		productoServices.update(producto);
	 
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productoServices.delete(id);
	}

}

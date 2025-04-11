package com.sinensia.donpollo.business.model.dtos;

/**
 * nombre: nombre del producto y familia, en formato:
 * 
 * "BOCADILLO SERRANITO [BOCADILLOS]"
 * 
 */
public class ProductoDTO1 {

	private String nombre;
	private Double precio;
	
	public ProductoDTO1(String nombre, Double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public Double getPrecio() {
		return precio;
	}
	
}

package com.sinensia.donpollo.business.model.dtos;

/**
 * nombre: El nombre en mayúsculas
 * descripción: Solo los 20 primeros carácteres de la descripcion.
 * 
 * 	Si la descripción sobrepasa los 25 caracteres, se recortan 12 y se incorporan 
 * 	puntos suspensivos. Ejemplo:
 * 
 * 	1234567890123456789012345
 * 	Delicioso batido de mi...
 * 
 */
public record ProductoDTO2(String nombre, String descripcion) {

}

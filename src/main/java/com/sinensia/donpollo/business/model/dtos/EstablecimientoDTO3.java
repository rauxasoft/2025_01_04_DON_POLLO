package com.sinensia.donpollo.business.model.dtos;

/**
 * nombre: El nombre del establecimiento en mayúsculas
 * 
 * 	ejemplo: "GRAN VIA II"
 * 
 * datos: Los datos de contacto (población, provincia y telefono) en una cadena. Ejemplo:
 * 
 * 	ejemplo: "BADALONA [BARCELONA] - 93 2202345"
 */
public record EstablecimientoDTO3(String nombre, String datos) {}

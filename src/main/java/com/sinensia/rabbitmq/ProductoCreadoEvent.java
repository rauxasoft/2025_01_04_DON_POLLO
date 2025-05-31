package com.sinensia.rabbitmq;

import java.io.Serializable;

public record ProductoCreadoEvent(
	Long id,
	String nombre,
	String fechaAlta, 			// ISO-8601
	double precio,
	String descripcion,
	String familia,
	boolean descatalogado
) implements Serializable {}

package com.sinensia.donpollo.business.model.dtos;

/**
 * numeroPedido: El número de pedido en forma de int
 * establecimiento: El nombre en mayúscula
 * fecha: La fecha en String
 * hora: La hora en String
 * estado: El estado en String
 * dependiente: nombre y apellidos del dependiente en mayúscula
 * 
 * Ejemplo:
 * 
 *  2455
 *  "LA VAGUADA"
 *  "24/11/2023"
 *  "13:21"
 *  "PENDIENTE"
 *  "CIFUENTES MERINO, CARLOTA"
 * 
 * Atención! Qué pasa si el dependiente no tiene segundo apellido?
 */

public record PedidoDTO1(int numeroPedido,
						 String establecimiento, 
						 String fecha, 
						 String hora, 
						 String estado, 
						 String dependiente) {
}

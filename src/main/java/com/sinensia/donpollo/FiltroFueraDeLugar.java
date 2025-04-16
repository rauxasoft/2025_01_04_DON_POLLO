package com.sinensia.donpollo;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Este filtro no debería estar aquí! package com.sinensia.donpollo;

// 1
@Component
@Order(1)
public class FiltroFueraDeLugar implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		// ENTRADA DEL FILTRO
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
	
	//	String method = request.getMethod();
	//	String url = request.getRequestURI();
	
		System.out.println("FILTRO 1 ENTRADA");
		/*
		 * 
		 * - Vamos a registrar TODAS las peticiones HTTP 
		 * - Para cada petición vamos a guardar:
		 * 
		 * 		- timestamp (System.currentTimeMillis())
		 * 		- método
		 * 		- URI
		 * 		- IP máquina cliente
		 * 		- elapsedTime: tiempo de respuesta en milisegundos
		 * 		- código HTTP de respuesta
		 * 
		 * Toda esta información son propiedades de un objeto (que tendremos que definir y al cuál tendremos que dar nombre)
		 * 
		 * - Vamos a necesitar una tabla para almacenar la información
		 * - Vamos a necesitar un repositorio
		 * - Vamos a necesitar un servicio (interface) con su correspondiente implementación
		 * 
		 * - Vamos a necesitar un contralodr REST para poder solicitar la información de las peticiones. 
		 *   Estasv vendrán devueltas de más nueva a más antiguo
		 * 
		 * http://localhost:8080/auditoria/logs
		 * 
		 * 
		 * INDICACIÓN INPORTANTE
		 * =====================
		 * 
		 * Si tuvieramos que "quitar" todo esto de la auditoría deberíamos poder hacerlo ÚNICAMENTE "eliminando cosas"
		 * 
		 */
	
		chain.doFilter(request, response);
		
		// SALIDA DEL FILTRO
		
		System.out.println("FILTRO 1 SALIDA");
		
	}

}

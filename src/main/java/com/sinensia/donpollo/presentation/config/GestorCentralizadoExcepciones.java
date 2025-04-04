package com.sinensia.donpollo.presentation.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sinensia.donpollo.business.config.BusinessException;

@ControllerAdvice
public class GestorCentralizadoExcepciones extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleArithmeticException(Exception ex){
		
		System.out.println(ex); // LOG
		
		return ResponseEntity.internalServerError().body(new ErrorResponse("Algo ha ido mal..."));
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex){
		
		System.out.println("OJO! Problema aritmético"); // LOG
		
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
	}

}

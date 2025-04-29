package com.sinensia.donpollo.presentation.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.common.presentation.ErrorResponse;

@ControllerAdvice
public class GestorCentralizadoExcepciones extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex){
		
		System.out.println(ex); // TODO crear un LOG
		
		return ResponseEntity.internalServerError().body(new ErrorResponse("Algo ha ido mal..."));
	}
	
	// **********************************************************************************************
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex){
		
		System.out.println("Se lanza una BusinessException"); // TODO crear un LOG
		
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
	}

	// **********************************************************************************************
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse("No se puede deserializar el objeto JSON");
		
		return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	// **********************************************************************************************

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse("No se han podido validar parámetros del request");
		
		return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	// **********************************************************************************************

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		String parametroInvalido = ex.getPropertyName();
		Object valorInvalido = ex.getValue();
		String tipoEsperado =  ex.getRequiredType().getSimpleName();
		
		ErrorResponse errorResponse = new ErrorResponse("El parámetro [" + parametroInvalido + "] devuelve [" + valorInvalido + "]. Se esperaba una valor de tipo [" + tipoEsperado + "]");
		
		return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	// **********************************************************************************************
	

}

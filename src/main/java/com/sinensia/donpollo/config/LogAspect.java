package com.sinensia.donpollo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LogAspect {
	
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	// TODO Necesitamos que los logs aporten  información sobre los argumentos que reciben los métodos.
	// por ejemplo... 
	
	// com.sinensia.donpollo.config.LogAspect  : invocado método read(245) de la clase ProductoServicesImpl
	
	@Before(value="execution(* com.sinensia.donpollo.business.services.impl.*.*(..))")
	public void logBusinessLayer(JoinPoint joinPoint) {
		
		String firma = joinPoint.getSignature().getName();
		String clase = joinPoint.getTarget().getClass().getSimpleName();
		
		logger.info("invocado método {}() de la clase {}", firma, clase);
	} 
	
	@Before(value="execution(* com.sinensia.donpollo.presentation.controllers.*.*(..))")
	public void logPresentationLayer(JoinPoint joinPoint) {
		
		String firma = joinPoint.getSignature().getName();
		String clase = joinPoint.getTarget().getClass().getSimpleName();
		
		logger.info("invocado método {}() de la clase {}", firma, clase);
	} 

}

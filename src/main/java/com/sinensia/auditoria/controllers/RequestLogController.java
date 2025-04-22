package com.sinensia.auditoria.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.auditoria.model.RequestLog;
import com.sinensia.auditoria.services.RequestLogServices;

@RestController
@RequestMapping("/auditoria")
public class RequestLogController {

	private RequestLogServices requestLogServices;
	
	public RequestLogController(RequestLogServices requestLogServices) {
		this.requestLogServices = requestLogServices;
	}
	
	@GetMapping("/logs")
	public List<RequestLog> getAll(){
		return requestLogServices.getAll();
	}
}

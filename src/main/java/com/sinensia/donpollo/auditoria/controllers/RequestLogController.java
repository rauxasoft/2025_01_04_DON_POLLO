package com.sinensia.donpollo.auditoria.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.auditoria.model.RequestLog;
import com.sinensia.donpollo.auditoria.services.RequestLogServices;

@RestController
@RequestMapping("/auditoria")
public class RequestLogController {

	private RequestLogServices requestLogServices;
	
	public RequestLogController(RequestLogServices requestLogServices) {
		this.requestLogServices = requestLogServices;
	}
	
	public List<RequestLog> getAll(){
		return requestLogServices.getAll();
	}
}

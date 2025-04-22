package com.sinensia.donpollo.auditoria.services.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.auditoria.model.RequestLog;
import com.sinensia.donpollo.auditoria.model.RequestLogRepository;
import com.sinensia.donpollo.auditoria.services.RequestLogServices;

@Service
public class RequestLogServicesImpl implements RequestLogServices {

	private final RequestLogRepository requestLogRepository;
	
	public RequestLogServicesImpl(RequestLogRepository requestLogRepository) {
		this.requestLogRepository = requestLogRepository;
	}
	
	@Override
	public List<RequestLog> getAll() {
		return requestLogRepository.findAll(Sort.by("id").descending());
	}

}

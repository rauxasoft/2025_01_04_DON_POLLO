package com.sinensia.auditoria.filter;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.sinensia.auditoria.integration.RequestLogRepository;
import com.sinensia.auditoria.model.RequestLog;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Profile("!test")
@Component
public class FiltroAuditor implements Filter {

	private final RequestLogRepository requestLogRepository;
	
	public FiltroAuditor(RequestLogRepository requestLogRepository) {
		this.requestLogRepository = requestLogRepository;
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		Long id = System.currentTimeMillis();
	
		chain.doFilter(req, res);

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		long elapsedTime = System.currentTimeMillis() - id;
		String ip = request.getRemoteAddr();
		String method = request.getMethod();
		String url = request.getRequestURI();
		int statusCode = response.getStatus();
		
		RequestLog requestLog = new RequestLog();
		
		requestLog.setTimeStamp(id);
		requestLog.setIp(ip);
		requestLog.setMethod(method);
		requestLog.setUrl(url);
		requestLog.setElapsedTime(elapsedTime);
		requestLog.setStatusCode(statusCode);
		
		requestLogRepository.save(requestLog);
		
	}

}

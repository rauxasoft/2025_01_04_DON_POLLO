package com.sinensia.donpollo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
		
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	
		try {
			
			String token = extractToken(request);
			
			if(token != null && jwtUtils.validateJwtToken(token)) {
				
				// No es necesario pero sí es conveniente, al margen de la validez del token, comprobar en la base de datos.
				
				String username = jwtUtils.getUserNameFromJwtToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Esta es la linea clave para autenticar!
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch(Exception e) {
			logger.error("No se puede establecer autenticación: {}", e);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private String extractToken(HttpServletRequest request) {
		
		String headerAuth = request.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		
		return null;
	}
}

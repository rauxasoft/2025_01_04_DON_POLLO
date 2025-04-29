package com.sinensia.donpollo.security.presentation;

import java.util.List;

public class JwtResponse {

	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	
	public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getRoles() {
		return roles;
	}
	
}
